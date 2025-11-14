package com.infy.project.Dto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SessionDTO {
    private Long id;
    private Long groupId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Boolean isPoll;
    private Boolean confirmed;
    private List<Integer> reminderOptions;                    // e.g. [30,15,14]
    private List<TimeSlotDTO> timeSlots;
    private List<TimeSlotVoteDTO> timeSlotVotes;
    private Map<String, Long> rsvpCounts;                     // yes/no/maybe counts
    private Map<Long, RsvpInfoDTO> rsvpByUser;                // userId -> info

    // Getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Boolean getIsPoll() { return isPoll; }
    public void setIsPoll(Boolean isPoll) { this.isPoll = isPoll; }

    public Boolean getConfirmed() { return confirmed; }
    public void setConfirmed(Boolean confirmed) { this.confirmed = confirmed; }

    public List<Integer> getReminderOptions() { return reminderOptions; }
    public void setReminderOptions(List<Integer> reminderOptions) { this.reminderOptions = reminderOptions; }

    public List<TimeSlotDTO> getTimeSlots() { return timeSlots; }
    public void setTimeSlots(List<TimeSlotDTO> timeSlots) { this.timeSlots = timeSlots; }

    public List<TimeSlotVoteDTO> getTimeSlotVotes() { return timeSlotVotes; }
    public void setTimeSlotVotes(List<TimeSlotVoteDTO> timeSlotVotes) { this.timeSlotVotes = timeSlotVotes; }

    public Map<String, Long> getRsvpCounts() { return rsvpCounts; }
    public void setRsvpCounts(Map<String, Long> rsvpCounts) { this.rsvpCounts = rsvpCounts; }

    public Map<Long, RsvpInfoDTO> getRsvpByUser() { return rsvpByUser; }
    public void setRsvpByUser(Map<Long, RsvpInfoDTO> rsvpByUser) { this.rsvpByUser = rsvpByUser; }
	public SessionDTO(Long id, Long groupId, String title, String description, LocalDateTime startTime,
			LocalDateTime endTime, Long createdBy, LocalDateTime createdAt, Boolean isPoll, Boolean confirmed,
			List<Integer> reminderOptions, List<TimeSlotDTO> timeSlots, List<TimeSlotVoteDTO> timeSlotVotes,
			Map<String, Long> rsvpCounts, Map<Long, RsvpInfoDTO> rsvpByUser) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.title = title;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.isPoll = isPoll;
		this.confirmed = confirmed;
		this.reminderOptions = reminderOptions;
		this.timeSlots = timeSlots;
		this.timeSlotVotes = timeSlotVotes;
		this.rsvpCounts = rsvpCounts;
		this.rsvpByUser = rsvpByUser;
	}
	public SessionDTO() {
		super();
	}
    
    
}
