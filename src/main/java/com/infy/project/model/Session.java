package com.infy.project.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "sessions")
public class Session {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long groupId;
    
    @Column(nullable = false, length = 255)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
    @Column(nullable = false)
    private LocalDateTime startTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
    @Column(nullable = false)
    private LocalDateTime endTime;
    
    @Column(nullable = false)
    private Long createdBy;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "reminder_options", length = 100)
    private String reminderOptions; // JSON array string: "[30,15,14]"
    
    @Column(nullable = false)
    private Boolean isPoll = false; // true if it's a poll session, false if confirmed
    
    @Column(nullable = false)
    private Boolean confirmed = false; // true if poll was finalized, false if still voting
    
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionRsvp> rsvps = new ArrayList<>();
    
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionVote> votes = new ArrayList<>();
    
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionTimeSlot> timeSlots = new ArrayList<>();
    
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionReminder> reminders = new ArrayList<>();
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getGroupId() {
        return groupId;
    }
    
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getReminderOptions() {
        return reminderOptions;
    }
    
    public void setReminderOptions(String reminderOptions) {
        this.reminderOptions = reminderOptions;
    }
    
    public Boolean getIsPoll() {
        return isPoll;
    }
    
    public void setIsPoll(Boolean isPoll) {
        this.isPoll = isPoll;
    }
    
    public Boolean getConfirmed() {
        return confirmed;
    }
    
    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
    
    public List<SessionRsvp> getRsvps() {
        return rsvps;
    }
    
    public void setRsvps(List<SessionRsvp> rsvps) {
        this.rsvps = rsvps;
    }
    
    public List<SessionVote> getVotes() {
        return votes;
    }
    
    public void setVotes(List<SessionVote> votes) {
        this.votes = votes;
    }
    
    public List<SessionTimeSlot> getTimeSlots() {
        return timeSlots;
    }
    
    public void setTimeSlots(List<SessionTimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
    
    public List<SessionReminder> getReminders() {
        return reminders;
    }
    
    public void setReminders(List<SessionReminder> reminders) {
        this.reminders = reminders;
    }
    
    public Session() {
    }

    
    
}

