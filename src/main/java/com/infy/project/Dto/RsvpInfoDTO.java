package com.infy.project.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RsvpInfoDTO {
    private String response; // "yes", "no", "maybe"
    private LocalDateTime timestamp;
    
    public RsvpInfoDTO() {}
    
    public RsvpInfoDTO(String response, LocalDateTime timestamp) {
        this.response = response;
        this.timestamp = timestamp;
    }

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
    
    
}

