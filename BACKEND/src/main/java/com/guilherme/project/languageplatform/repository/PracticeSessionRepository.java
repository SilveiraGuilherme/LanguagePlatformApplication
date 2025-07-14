package com.guilherme.project.languageplatform.repository;

import com.guilherme.project.languageplatform.entity.PracticeSession;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Long> {

    Optional<PracticeSession> findByStudentStudentIDAndSessionStatus(Long studentId,
            PracticeSession.SessionStatus sessionStatus);

    Optional<PracticeSession> findByStudentStudentIDAndCompletedFalse(Long studentID);
}