package com.guilherme.project.languageplatform.service;

import com.guilherme.project.languageplatform.entity.PracticeSession;
import com.guilherme.project.languageplatform.repository.PracticeSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracticeSessionService {

    @Autowired
    private PracticeSessionRepository practiceSessionRepository;

    // Get all sessions
    public List<PracticeSession> getAllPracticeSessions() {
        return practiceSessionRepository.findAll();
    }

    // Get session by ID
    public Optional<PracticeSession> getPracticeSessionById(Long id) {
        return practiceSessionRepository.findById(id);
    }

    // Get student's ongoing session
    public Optional<PracticeSession> getOngoingSessionByStudentId(Long studentId) {
        return practiceSessionRepository.findByStudentStudentIDAndSessionStatus(studentId,
                PracticeSession.SessionStatus.ONGOING);
    }

    // Save new session
    public PracticeSession savePracticeSession(PracticeSession session) {
        return practiceSessionRepository.save(session);
    }

    // Update session status and end time
    public PracticeSession updatePracticeSession(Long id, PracticeSession updatedSession) {
        PracticeSession session = practiceSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setSessionStatus(updatedSession.getSessionStatus());
        session.setEndTime(updatedSession.getEndTime());

        return practiceSessionRepository.save(session);
    }

    // Delete session by ID
    public void deletePracticeSession(Long id) {
        practiceSessionRepository.deleteById(id);
    }
}