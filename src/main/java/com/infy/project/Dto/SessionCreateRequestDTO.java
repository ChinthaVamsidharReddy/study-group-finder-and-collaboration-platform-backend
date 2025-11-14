package com.infy.project.Dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SessionCreateRequestDTO {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Integer> reminderOptions; // [30, 15, 14] minutes before
    private Boolean isPoll = false;
    private List<TimeSlotDTO> timeSlots; // For poll sessions with multiple time options
    private Long createdBy;
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
	public List<Integer> getReminderOptions() {
		return reminderOptions;
	}
	public void setReminderOptions(List<Integer> reminderOptions) {
		this.reminderOptions = reminderOptions;
	}
	public Boolean getIsPoll() {
		return isPoll;
	}
	public void setIsPoll(Boolean isPoll) {
		this.isPoll = isPoll;
	}
	public List<TimeSlotDTO> getTimeSlots() {
		return timeSlots;
	}
	public void setTimeSlots(List<TimeSlotDTO> timeSlots) {
		this.timeSlots = timeSlots;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public SessionCreateRequestDTO(String title, String description, LocalDateTime startTime, LocalDateTime endTime,
			List<Integer> reminderOptions, Boolean isPoll, List<TimeSlotDTO> timeSlots, Long createdBy) {
		super();
		this.title = title;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.reminderOptions = reminderOptions;
		this.isPoll = isPoll;
		this.timeSlots = timeSlots;
		this.createdBy = createdBy;
	}
	public SessionCreateRequestDTO() {
		super();
	}
    
    
}

