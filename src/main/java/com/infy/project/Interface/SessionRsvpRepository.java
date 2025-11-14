package com.infy.project.Interface;

import com.infy.project.model.SessionRsvp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface SessionRsvpRepository extends JpaRepository<SessionRsvp, Long> {
    Optional<SessionRsvp> findBySessionIdAndUserId(Long sessionId, Long userId);
    List<SessionRsvp> findBySessionId(Long sessionId);
    long countBySessionIdAndResponse(Long sessionId, String response);
}

