package com.infy.project.Interface;

import com.infy.project.model.SessionReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionReminderRepository extends JpaRepository<SessionReminder, Long> {
    Optional<SessionReminder> findBySessionIdAndUserIdAndReminderOffset(Long sessionId, Long userId, Integer reminderOffset);
    List<SessionReminder> findBySessionId(Long sessionId);
}

