package com.infy.project.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import jakarta.validation.*;
import com.infy.project.Dto.*;
import com.infy.project.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(
    origins = {
        "https://study-group-finder-and-collaboratio.vercel.app",
        "http://localhost:3000"
    },
    allowCredentials = "true"
)

public class SessionController {
    private static final Logger log = LoggerFactory.getLogger(SessionController.class);
    
    @Autowired
    private SessionService sessionService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    /**
     * Create a session (poll or confirmed)
     * POST /api/groups/{groupId}/sessions
     */
    @PostMapping("/groups/{groupId}/sessions")
    public ResponseEntity<SessionDTO> createSession(
            @PathVariable Long groupId,
            @Valid @RequestBody SessionCreateRequestDTO request) {
        try {
            SessionDTO session = sessionService.createSession(groupId, request);
            
            // Broadcast via WebSocket
            SessionWebSocketPayload payload = new SessionWebSocketPayload("created", groupId, session);
            messagingTemplate.convertAndSend("/topic/group." + groupId, payload);
            log.info("Session created: id={} groupId={}", session.getId(), groupId);
            return ResponseEntity.status(HttpStatus.CREATED).body(session);
        } catch (Exception e) {
            log.error("Create session failed for group {}: {}", groupId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Get all sessions for a group
     * GET /api/groups/{groupId}/sessions
     */
    @GetMapping("/groups/{groupId}/sessions")
    public ResponseEntity<List<SessionDTO>> getGroupSessions(@PathVariable Long groupId) {
        try {
            List<SessionDTO> sessions = sessionService.getGroupSessions(groupId);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            log.error("Get sessions failed for group {}: {}", groupId, e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Vote on a poll session
     * POST /api/sessions/{id}/vote
     */
    @PostMapping("/{id}/vote")
    public ResponseEntity<SessionDTO> voteOnSession(
            @PathVariable Long id,
            @Valid @RequestBody SessionVoteRequestDTO request) {
    	System.out.println("poll voye -1");
        try {
            // allow alias: if startTime provided, map to votedTimeSlot
        	System.out.println("poll voye -2");
            if (request.getVotedTimeSlot() == null && request.getStartTime() != null) {
                request.setVotedTimeSlot(request.getStartTime());
            }
            SessionDTO session = sessionService.voteOnSession(id, request);
            
            // Broadcast via WebSocket
            SessionWebSocketPayload payload = new SessionWebSocketPayload("voted", session.getGroupId(), session);
            messagingTemplate.convertAndSend("/topic/group." + session.getGroupId(), payload);
            
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            log.error("Vote failed for session {}: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Finalize a poll session
     * POST /api/sessions/{id}/finalize
     */
    @PostMapping("/{id}/finalize")
    public ResponseEntity<SessionDTO> finalizeSession(
            @PathVariable Long id,
            @RequestParam Long adminId,
            @RequestParam LocalDateTime selectedStartTime) {
        try {
            SessionDTO session = sessionService.finalizeSession(id, adminId, selectedStartTime);
            
            // Broadcast via WebSocket
            SessionWebSocketPayload payload = new SessionWebSocketPayload("finalized", session.getGroupId(), session);
            messagingTemplate.convertAndSend("/topic/group." + session.getGroupId(), payload);
            
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            log.error("Finalize failed for session {}: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * RSVP to a session
     * POST /api/sessions/{id}/rsvp
     */
    @PostMapping("/sessions/{id}/rsvp")
    public ResponseEntity<SessionDTO> rsvpToSession(
            @PathVariable Long id,
            @Valid @RequestBody SessionRsvpRequestDTO request) {
        try {
            SessionDTO session = sessionService.rsvpToSession(id, request);
            
            // Broadcast via WebSocket
            SessionWebSocketPayload payload = new SessionWebSocketPayload("rsvp", session.getGroupId(), session);
            messagingTemplate.convertAndSend("/topic/group." + session.getGroupId(), payload);
            
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            log.error("RSVP failed for session {}: {}", id, e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Delete a session
     * DELETE /api/sessions/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(
            @PathVariable Long id,
            @RequestParam Long userId) {
        try {
            SessionDTO session = sessionService.getSessionById(id);
            sessionService.deleteSession(id, userId);
            
            // Broadcast via WebSocket
            SessionDTO deletedSession = new SessionDTO();
            deletedSession.setId(id);
            deletedSession.setGroupId(session.getGroupId());
            SessionWebSocketPayload payload = new SessionWebSocketPayload("deleted", session.getGroupId(), deletedSession);
            messagingTemplate.convertAndSend("/topic/group." + session.getGroupId(), payload);
            
            return ResponseEntity.ok("Session deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Get session by ID
     * GET /api/sessions/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable Long id) {
        try {
            SessionDTO session = sessionService.getSessionById(id);
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

