package com.infy.project.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_votes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"session_id", "user_id"})
})
public class SessionVote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "voted_time_slot", nullable = false)
    private LocalDateTime votedTimeSlot;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
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
    
    public LocalDateTime getVotedTimeSlot() {
        return votedTimeSlot;
    }
    
    public void setVotedTimeSlot(LocalDateTime votedTimeSlot) {
        this.votedTimeSlot = votedTimeSlot;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public SessionVote() {
    }
}

