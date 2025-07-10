package com.guilherme.project.languageplatform.repository;

import com.guilherme.project.languageplatform.entity.PracticeSession;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeSessionRepository extends JpaRepository<PracticeSession, Integer> {

    Optional<PracticeSession> findByStudentStudentIDAndSessionStatus(Integer studentId,
            PracticeSession.SessionStatus sessionStatus);
}