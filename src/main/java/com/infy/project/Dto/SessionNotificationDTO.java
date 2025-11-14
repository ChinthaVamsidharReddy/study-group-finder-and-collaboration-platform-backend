package com.infy.project.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SessionNotificationDTO {
    private String type = "session_reminder";
    private Long sessionId;
    private Long groupId;
    private String title;
    private LocalDateTime startTime;
    private Integer minutesBefore;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getSessionId() {
		return sessionId;
	}
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
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
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public Integer getMinutesBefore() {
		return minutesBefore;
	}
	public void setMinutesBefore(Integer minutesBefore) {
		this.minutesBefore = minutesBefore;
	}
	public SessionNotificationDTO(String type, Long sessionId, Long groupId, String title, LocalDateTime startTime,
			Integer minutesBefore) {
		super();
		this.type = type;
		this.sessionId = sessionId;
		this.groupId = groupId;
		this.title = title;
		this.startTime = startTime;
		this.minutesBefore = minutesBefore;
	}
	public SessionNotificationDTO() {
		super();
	}
    
    
}

