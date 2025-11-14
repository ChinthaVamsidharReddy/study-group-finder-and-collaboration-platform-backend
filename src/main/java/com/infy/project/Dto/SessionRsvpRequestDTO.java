package com.infy.project.Dto;

import lombok.Data;

@Data
public class SessionRsvpRequestDTO {
    private Long userId;
    private String response; // "yes", "no", "maybe"
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public SessionRsvpRequestDTO(Long userId, String response) {
		super();
		this.userId = userId;
		this.response = response;
	}
	public SessionRsvpRequestDTO() {
		super();
	}
    
    
}

