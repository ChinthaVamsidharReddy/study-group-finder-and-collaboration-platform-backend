package com.infy.project.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SessionVoteRequestDTO {
    private Long userId;
    private LocalDateTime votedTimeSlot;
    // alias accepted from frontend
    private LocalDateTime startTime;
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
	public SessionVoteRequestDTO(Long userId, LocalDateTime votedTimeSlot) {
		super();
		this.userId = userId;
		this.votedTimeSlot = votedTimeSlot;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public SessionVoteRequestDTO() {
		super();
	}
    
	
    
    
}

