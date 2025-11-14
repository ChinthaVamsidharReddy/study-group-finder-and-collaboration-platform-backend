package com.infy.project.Service;

import com.infy.project.Dto.*;
import com.infy.project.Interface.*;
import com.infy.project.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SessionService {
    
    @Autowired
    private SessionRepository sessionRepository;
    
    @Autowired
    private SessionRsvpRepository sessionRsvpRepository;
    
    @Autowired
    private SessionVoteRepository sessionVoteRepository;
    
    @Autowired
    private SessionTimeSlotRepository sessionTimeSlotRepository;
    
    @Autowired
    private SessionReminderRepository sessionReminderRepository;
    
    @Autowired
    private RegisterRepository registerRepository;
    
    @Autowired
    private GroupRepository groupRepository;
    
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    
    /**
     * Create a session (poll or confirmed)
     */
    @Transactional
    public SessionDTO createSession(Long groupId, SessionCreateRequestDTO request) {
        // Verify group exists
        if (!groupRepository.existsById(groupId)) {
            throw new RuntimeException("Group not found");
        }
        
        Session session = new Session();
        session.setGroupId(groupId);
        session.setTitle(request.getTitle());
        session.setDescription(request.getDescription());
        session.setCreatedBy(request.getCreatedBy());
        session.setCreatedAt(LocalDateTime.now());
        session.setIsPoll(request.getIsPoll() != null ? request.getIsPoll() : false);
        session.setConfirmed(!session.getIsPoll());
        
        // Set reminder options as JSON string
        if (request.getReminderOptions() != null && !request.getReminderOptions().isEmpty()) {
            session.setReminderOptions(request.getReminderOptions().toString());
        } else {
            session.setReminderOptions("[30,15,14]"); // Default
        }
        
        if (session.getIsPoll()) {
            // Poll session with multiple time slots
            if (request.getTimeSlots() == null || request.getTimeSlots().isEmpty()) {
                throw new RuntimeException("Poll sessions must have at least one time slot");
            }
            
            // Create time slots (will be finalized later)
            for (TimeSlotDTO slot : request.getTimeSlots()) {
                SessionTimeSlot timeSlot = new SessionTimeSlot();
                timeSlot.setSession(session);
                timeSlot.setStartTime(slot.getStartTime());
                timeSlot.setEndTime(slot.getEndTime());
                timeSlot.setVoteCount(0);
                session.getTimeSlots().add(timeSlot);
            }
            
            // For poll, startTime/endTime are null initially
            session.setStartTime(null);
            session.setEndTime(null);
        } else {
            // Confirmed session with single time
            if (request.getStartTime() == null || request.getEndTime() == null) {
                throw new RuntimeException("Confirmed sessions must have startTime and endTime");
            }
            session.setStartTime(request.getStartTime());
            session.setEndTime(request.getEndTime());
        }
        
        Session saved = sessionRepository.save(session);
        return convertToDTO(saved);
    }
    
    /**
     * Vote on a poll session time slot
     */
    @Transactional
    public SessionDTO voteOnSession(Long sessionId, SessionVoteRequestDTO request) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Session not found"));
        
        if (!session.getIsPoll() || session.getConfirmed()) {
            throw new RuntimeException("Cannot vote on non-poll or finalized session");
        }
        
        // Check if user already voted (idempotent - update if exists)
        Optional<SessionVote> existingVote = sessionVoteRepository.findBySessionIdAndUserId(sessionId, request.getUserId());
        
        if (existingVote.isPresent()) {
            // Update existing vote
            SessionVote vote = existingVote.get();
            // Decrement old time slot vote count
            SessionTimeSlot oldSlot = session.getTimeSlots().stream()
                .filter(ts -> ts.getStartTime().equals(vote.getVotedTimeSlot()))
                .findFirst()
                .orElse(null);
            if (oldSlot != null) {
                oldSlot.setVoteCount(Math.max(0, oldSlot.getVoteCount() - 1));
            }
            
            vote.setVotedTimeSlot(request.getVotedTimeSlot());
            vote.setTimestamp(LocalDateTime.now());
            sessionVoteRepository.save(vote);
        } else {
            // Create new vote
            SessionVote vote = new SessionVote();
            vote.setSession(session);
            vote.setUserId(request.getUserId());
            vote.setVotedTimeSlot(request.getVotedTimeSlot());
            vote.setTimestamp(LocalDateTime.now());
            sessionVoteRepository.save(vote);
        }
        
        // Increment new time slot vote count
        SessionTimeSlot newSlot = session.getTimeSlots().stream()
            .filter(ts -> ts.getStartTime().equals(request.getVotedTimeSlot()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Time slot not found"));
        newSlot.setVoteCount(newSlot.getVoteCount() + 1);
        sessionTimeSlotRepository.save(newSlot);
        
        // Refresh session
        session = sessionRepository.findById(sessionId).orElse(session);
        return convertToDTO(session);
    }
    
    /**
     * Finalize a poll session (select winning time slot)
     */
    @Transactional
    public SessionDTO finalizeSession(Long sessionId, Long adminId, LocalDateTime selectedStartTime) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Session not found"));
        
        if (!session.getIsPoll()) {
            throw new RuntimeException("Only poll sessions can be finalized");
        }
        
        if (session.getConfirmed()) {
            throw new RuntimeException("Session already finalized");
        }
        
        // Verify admin (group admin) can finalize
        var gm = groupMemberRepository.findByGroupIdAndUserId(session.getGroupId(), adminId)
                .orElseThrow(() -> new RuntimeException("User not in group"));
        if (gm.getRole() != com.infy.project.model.Role.ADMIN) {
            throw new RuntimeException("Only group admins can finalize");
        }
        
        // Find selected time slot
        SessionTimeSlot selectedSlot = session.getTimeSlots().stream()
            .filter(ts -> ts.getStartTime().equals(selectedStartTime))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Selected time slot not found"));
        
        // Set session to confirmed
        session.setConfirmed(true);
        session.setStartTime(selectedSlot.getStartTime());
        session.setEndTime(selectedSlot.getEndTime());
        
        Session saved = sessionRepository.save(session);
        return convertToDTO(saved);
    }
    
    /**
     * RSVP to a session
     */
    @Transactional
    public SessionDTO rsvpToSession(Long sessionId, SessionRsvpRequestDTO request) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Session not found"));
        
        if (session.getIsPoll() && !session.getConfirmed()) {
            throw new RuntimeException("Cannot RSVP to unfinalized poll session");
        }
        
        if (!Arrays.asList("yes", "no", "maybe").contains(request.getResponse().toLowerCase())) {
            throw new RuntimeException("Invalid RSVP response. Must be 'yes', 'no', or 'maybe'");
        }
        
        // Check if user already RSVPed (idempotent - update if exists)
        Optional<SessionRsvp> existingRsvp = sessionRsvpRepository.findBySessionIdAndUserId(sessionId, request.getUserId());
        
        if (existingRsvp.isPresent()) {
            SessionRsvp rsvp = existingRsvp.get();
            rsvp.setResponse(request.getResponse().toLowerCase());
            rsvp.setTimestamp(LocalDateTime.now());
            sessionRsvpRepository.save(rsvp);
        } else {
            SessionRsvp rsvp = new SessionRsvp();
            rsvp.setSession(session);
            rsvp.setUserId(request.getUserId());
            rsvp.setResponse(request.getResponse().toLowerCase());
            rsvp.setTimestamp(LocalDateTime.now());
            sessionRsvpRepository.save(rsvp);
        }
        
        // Refresh session
        session = sessionRepository.findById(sessionId).orElse(session);
        return convertToDTO(session);
    }
    
    /**
     * Get all sessions for a group
     */
    public List<SessionDTO> getGroupSessions(Long groupId) {
        List<Session> sessions = sessionRepository.findByGroupIdOrderByStartTimeAsc(groupId);
        return sessions.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Get session by ID
     */
    public SessionDTO getSessionById(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Session not found"));
        return convertToDTO(session);
    }
    
    /**
     * Delete a session
     */
    @Transactional
    public void deleteSession(Long sessionId, Long userId) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new RuntimeException("Session not found"));
        
        // Only creator can delete
        if (!session.getCreatedBy().equals(userId)) {
            throw new RuntimeException("Only session creator can delete");
        }
        
        sessionRepository.delete(session);
    }
    
    /**
     * Get sessions that need reminders (for scheduled job)
     */
    public List<Session> getSessionsNeedingReminders(LocalDateTime now) {
        List<Session> allSessions = sessionRepository.findAll();
        List<Session> needingReminders = new ArrayList<>();
        
        for (Session session : allSessions) {
            if (!session.getConfirmed() || session.getStartTime() == null) {
                continue;
            }
            
            // Parse reminder options
            List<Integer> offsets = parseReminderOptions(session.getReminderOptions());
            LocalDateTime sessionStart = session.getStartTime();
            
            for (Integer offset : offsets) {
                LocalDateTime reminderTime = sessionStart.minusMinutes(offset);
                // Check if reminder should be sent now (within 1 minute window)
                if (reminderTime.isBefore(now) || reminderTime.isAfter(now.minusMinutes(1))) {
                    if (reminderTime.isBefore(now) && reminderTime.isAfter(now.minusMinutes(1))) {
                        needingReminders.add(session);
                        break;
                    }
                }
            }
        }
        
        return needingReminders;
    }
    
    /**
     * Mark reminder as sent (idempotent)
     */
    @Transactional
    public void markReminderSent(Long sessionId, Long userId, Integer reminderOffset) {
        Optional<SessionReminder> existing = sessionReminderRepository
            .findBySessionIdAndUserIdAndReminderOffset(sessionId, userId, reminderOffset);
        
        if (existing.isEmpty()) {
            SessionReminder reminder = new SessionReminder();
            Session session = sessionRepository.findById(sessionId).orElseThrow();
            reminder.setSession(session);
            reminder.setUserId(userId);
            reminder.setReminderOffset(reminderOffset);
            reminder.setSentAt(LocalDateTime.now());
            sessionReminderRepository.save(reminder);
        }
    }
    
    /**
     * Check if reminder was already sent
     */
    public boolean isReminderSent(Long sessionId, Long userId, Integer reminderOffset) {
        return sessionReminderRepository
            .findBySessionIdAndUserIdAndReminderOffset(sessionId, userId, reminderOffset)
            .isPresent();
    }
    
    /**
     * Convert Session entity to DTO
     */
    private SessionDTO convertToDTO(Session session) {
        SessionDTO dto = new SessionDTO();
        dto.setId(session.getId());
        dto.setGroupId(session.getGroupId());
        dto.setTitle(session.getTitle());
        dto.setDescription(session.getDescription());
        dto.setStartTime(session.getStartTime());
        dto.setEndTime(session.getEndTime());
        dto.setCreatedBy(session.getCreatedBy());
        dto.setCreatedAt(session.getCreatedAt());
        dto.setIsPoll(session.getIsPoll());
        dto.setConfirmed(session.getConfirmed());
        
        // Parse reminder options
        dto.setReminderOptions(parseReminderOptions(session.getReminderOptions()));
        
        // Convert time slots
        if (session.getIsPoll()) {
            List<TimeSlotDTO> timeSlots = session.getTimeSlots().stream()
                .map(ts -> {
                    TimeSlotDTO slot = new TimeSlotDTO();
                    slot.setStartTime(ts.getStartTime());
                    slot.setEndTime(ts.getEndTime());
                    return slot;
                })
                .collect(Collectors.toList());
            dto.setTimeSlots(timeSlots);
            
            // Convert time slot votes
            List<TimeSlotVoteDTO> voteDTOs = session.getTimeSlots().stream()
                .map(ts -> new TimeSlotVoteDTO(ts.getStartTime(), ts.getEndTime(), ts.getVoteCount()))
                .collect(Collectors.toList());
            dto.setTimeSlotVotes(voteDTOs);
        }
        
        // Calculate RSVP counts
        Map<String, Long> rsvpCounts = new HashMap<>();
        rsvpCounts.put("yes", sessionRsvpRepository.countBySessionIdAndResponse(session.getId(), "yes"));
        rsvpCounts.put("no", sessionRsvpRepository.countBySessionIdAndResponse(session.getId(), "no"));
        rsvpCounts.put("maybe", sessionRsvpRepository.countBySessionIdAndResponse(session.getId(), "maybe"));
        dto.setRsvpCounts(rsvpCounts);
        
        // Convert RSVP by user
        Map<Long, RsvpInfoDTO> rsvpByUser = new HashMap<>();
        List<SessionRsvp> rsvps = sessionRsvpRepository.findBySessionId(session.getId());
        for (SessionRsvp rsvp : rsvps) {
            rsvpByUser.put(rsvp.getUserId(), new RsvpInfoDTO(rsvp.getResponse(), rsvp.getTimestamp()));
        }
        dto.setRsvpByUser(rsvpByUser);
        
        return dto;
    }
    
    /**
     * Parse reminder options from JSON string
     */
    private List<Integer> parseReminderOptions(String reminderOptions) {
        if (reminderOptions == null || reminderOptions.isEmpty()) {
            return Arrays.asList(30, 15, 14);
        }
        
        try {
            // Remove brackets and split by comma
            String cleaned = reminderOptions.replace("[", "").replace("]", "").trim();
            if (cleaned.isEmpty()) {
                return Arrays.asList(30, 15, 14);
            }
            
            return Arrays.stream(cleaned.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        } catch (Exception e) {
            return Arrays.asList(30, 15, 14);
        }
    }
}

