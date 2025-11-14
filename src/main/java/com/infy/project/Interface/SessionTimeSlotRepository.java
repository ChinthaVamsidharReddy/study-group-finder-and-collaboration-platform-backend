package com.infy.project.Interface;

import com.infy.project.model.SessionTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SessionTimeSlotRepository extends JpaRepository<SessionTimeSlot, Long> {
    List<SessionTimeSlot> findBySessionIdOrderByStartTimeAsc(Long sessionId);
}

