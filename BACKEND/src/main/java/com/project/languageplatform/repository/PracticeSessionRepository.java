package com.project.languageplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.languageplatform.entity.PracticeSession;

public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {

    Optional<PracticeSession> findByUserUserIDAndSessionStatus(Long userID,
            PracticeSession.SessionStatus sessionStatus);
}