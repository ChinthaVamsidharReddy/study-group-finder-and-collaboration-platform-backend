package com.infy.project.Interface;

import com.infy.project.model.SessionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface SessionVoteRepository extends JpaRepository<SessionVote, Long> {
    Optional<SessionVote> findBySessionIdAndUserId(Long sessionId, Long userId);
    List<SessionVote> findBySessionId(Long sessionId);
    long countBySessionId(Long sessionId);
}

