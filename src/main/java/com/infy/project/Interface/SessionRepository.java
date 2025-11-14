package com.infy.project.Interface;

import com.infy.project.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByGroupIdOrderByStartTimeAsc(Long groupId);
    List<Session> findByGroupIdAndConfirmedTrueOrderByStartTimeAsc(Long groupId);
    List<Session> findByGroupIdAndIsPollTrueAndConfirmedFalse(Long groupId);
}

