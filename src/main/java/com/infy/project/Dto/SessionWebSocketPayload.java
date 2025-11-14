package com.infy.project.Dto;

import lombok.Data;

@Data
public class SessionWebSocketPayload {
    private String type = "session";
    private String action; // "created", "voted", "finalized", "rsvp", "deleted"
    private Long groupId;
    private SessionDTO session;
    
    public SessionWebSocketPayload() {}
    
    public SessionWebSocketPayload(String action, Long groupId, SessionDTO session) {
        this.action = action;
        this.groupId = groupId;
        this.session = session;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public SessionDTO getSession() {
		return session;
	}

	public void setSession(SessionDTO session) {
		this.session = session;
	}
    
    
}

