package com.project.languageplatform.repository;

// Repository interface for managing PracticeSession entities using JPA.

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.languageplatform.entity.PracticeSession;

public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {

    // Retrieves an optional ongoing or completed session for a given user.
    Optional<PracticeSession> findByUserUserIDAndSessionStatus(Long userID,
            PracticeSession.SessionStatus sessionStatus);
}