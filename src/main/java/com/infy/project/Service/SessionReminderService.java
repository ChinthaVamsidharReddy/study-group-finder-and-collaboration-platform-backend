package com.infy.project.Service;

import com.infy.project.Dto.SessionNotificationDTO;
import com.infy.project.Interface.*;
import com.infy.project.model.Session;

import jakarta.mail.internet.MimeMessage;

import com.infy.project.model.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionReminderService {
    
    @Autowired
    private SessionService sessionService;
    
    @Autowired
    private MailService mailService;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private RegisterRepository registerRepository;
    
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    
    /**
     * Scheduled job runs every minute to check for sessions needing reminders
     */
    @Scheduled(cron = "0 * * * * *") // Every minute
    public void sendScheduledReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Session> sessionsNeedingReminders = sessionService.getSessionsNeedingReminders(now);
        
        for (Session session : sessionsNeedingReminders) {
            if (!session.getConfirmed() || session.getStartTime() == null) {
                continue;
            }
            
            // Parse reminder options
            List<Integer> offsets = parseReminderOptions(session.getReminderOptions());
            LocalDateTime sessionStart = session.getStartTime();
            
            // Skip if session already started
            if (sessionStart.isBefore(now)) {
                continue;
            }
            
            // Get all group members (send reminders to all, not just RSVP yes/maybe)
            // In production, you might want to filter by RSVP status
            List<Long> memberIds = groupMemberRepository.findByGroupId(session.getGroupId())
                .stream()
                .map(m -> m.getUser().getId())
                .collect(Collectors.toList());
            
            for (Integer offset : offsets) {
                LocalDateTime reminderTime = sessionStart.minusMinutes(offset);
                
                // Check if reminder should be sent now (within 1 minute window)
                // Reminder time should be in the past but within last minute
             // Check if within a small window (e.g., 10 seconds) before reminder time
                if (now.isAfter(reminderTime.minusSeconds(10)) && now.isBefore(reminderTime.plusSeconds(10))) {
                    for (Long userId : memberIds) {
                        if (!sessionService.isReminderSent(session.getId(), userId, offset)) {
                            Register user = registerRepository.findById(userId).orElse(null);
                            if (user != null) {
                                sendReminder(session, user, offset);
                                sessionService.markReminderSent(session.getId(), userId, offset);
                            }
                        }
                    }
                }

            }
        }
    }
    
    /**
     * Send reminder via email and WebSocket
     */
    private void sendReminder(Session session, Register user, Integer minutesBefore) {
        try {
            // Send email
        	 String subject = String.format("📅 Reminder: %s starts in %d minutes", session.getTitle(), minutesBefore);

             // ✅ HTML email body
             String htmlBody = """
                 <div style="font-family: 'Segoe UI', Arial, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #e2e8f0; border-radius: 10px; padding: 20px; background-color: #f9fafb;">
                     <h2 style="color: #2563eb; text-align: center;">Study Session Reminder ⏰</h2>
                     <p style="font-size: 16px; color: #1f2937;">Hello <strong>%s</strong>,</p>

                     <p style="font-size: 15px; color: #374151;">This is a friendly reminder that you have an upcoming study session:</p>

                     <div style="background-color: #fff; border: 1px solid #e5e7eb; border-radius: 8px; padding: 16px; margin: 20px 0;">
                         <h3 style="color: #111827; margin: 0;">📘 %s</h3>
                         <p style="color: #6b7280; margin-top: 4px;">%s</p>

                         <table style="width: 100%%; margin-top: 10px;">
                             <tr>
                                 <td style="padding: 6px 0;"><strong>🗓 Date:</strong></td>
                                 <td>%s</td>
                             </tr>
                             <tr>
                                 <td style="padding: 6px 0;"><strong>⏰ Starts In:</strong></td>
                                 <td>%d minutes</td>
                             </tr>
                             <tr>
                                 <td style="padding: 6px 0;"><strong>👥 Group:</strong></td>
                                 <td>%d</td>
                             </tr>
                         </table>
                     </div>

                     <div style="text-align: center; margin-top: 20px;">
                         <a href="https://study-group-finder-and-collaboratio.vercel.app/dashboard"
                            style="background-color: #2563eb; color: white; padding: 12px 24px; text-decoration: none; border-radius: 6px; font-weight: 600;">
                             View in Calendar
                         </a>
                     </div>

                     <p style="font-size: 13px; color: #6b7280; margin-top: 30px; text-align: center;">
                         – Study Group Finder & Collaboration Platform Team –
                     </p>
                 </div>
                 """.formatted(
                     user.getName(),
                     session.getTitle(),
                     session.getDescription() != null ? session.getDescription() : "No description provided.",
                     session.getStartTime().toString(),
                     minutesBefore,
                     session.getGroupId()
                 );
            
          // ✅ Use MimeMessage instead of SimpleMailMessage
             MimeMessage mimeMessage = javaMailSender.createMimeMessage();
             MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

             helper.setTo(user.getEmail());
             helper.setSubject(subject);
             helper.setText(htmlBody, true); // true = HTML mode

             javaMailSender.send(mimeMessage);
             System.out.println("✅ HTML reminder email sent to " + user.getEmail());
            
            // Send WebSocket notification
            SessionNotificationDTO notification = new SessionNotificationDTO();
            notification.setSessionId(session.getId());
            notification.setGroupId(session.getGroupId());
            notification.setTitle(session.getTitle());
            notification.setStartTime(session.getStartTime());
            notification.setMinutesBefore(minutesBefore);
            notification.setType("reminder");
            
            messagingTemplate.convertAndSend(
                "/topic/group." + session.getGroupId(),
                notification
            );
            
        } catch (Exception e) {
            System.err.println("Error sending reminder for session " + session.getId() + ": " + e.getMessage());
        }
    }
    
    /**
     * Parse reminder options from JSON string
     */
    private List<Integer> parseReminderOptions(String reminderOptions) {
        if (reminderOptions == null || reminderOptions.isEmpty()) {
            return List.of(30, 15, 14);
        }
        
        try {
            String cleaned = reminderOptions.replace("[", "").replace("]", "").trim();
            if (cleaned.isEmpty()) {
                return List.of(30, 15, 14);
            }
            
            return java.util.Arrays.stream(cleaned.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of(30, 15, 14);
        }
    }
}

