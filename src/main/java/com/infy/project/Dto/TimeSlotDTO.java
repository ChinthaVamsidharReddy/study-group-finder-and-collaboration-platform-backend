package com.infy.project.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TimeSlotDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
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
	public TimeSlotDTO(LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public TimeSlotDTO() {
		super();
	}
    
    
}

