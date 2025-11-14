package com.infy.project.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_reminders", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"session_id", "user_id", "reminder_offset"})
})
public class SessionReminder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "reminder_offset", nullable = false)
    private Integer reminderOffset; // minutes before session (30, 15, 14, etc.)
    
    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Session getSession() {
        return session;
    }
    
    public void setSession(Session session) {
        this.session = session;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Integer getReminderOffset() {
        return reminderOffset;
    }
    
    public void setReminderOffset(Integer reminderOffset) {
        this.reminderOffset = reminderOffset;
    }
    
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    
    public SessionReminder() {
    }
}

