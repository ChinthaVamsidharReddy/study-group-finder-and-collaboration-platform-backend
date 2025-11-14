package com.infy.project.Dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TimeSlotVoteDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer voteCount;
    
    public TimeSlotVoteDTO() {}
    
    public TimeSlotVoteDTO(LocalDateTime startTime, LocalDateTime endTime, Integer voteCount) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.voteCount = voteCount;
    }
}

