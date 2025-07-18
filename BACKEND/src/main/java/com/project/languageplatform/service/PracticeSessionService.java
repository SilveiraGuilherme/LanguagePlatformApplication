package com.project.languageplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.languageplatform.entity.PracticeSession;
import com.project.languageplatform.entity.User;
import com.project.languageplatform.entity.PracticeSession.SessionStatus;
import com.project.languageplatform.repository.PracticeSessionRepository;
import com.project.languageplatform.repository.UserRepository;

import java.time.LocalDateTime;
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
    public Optional<PracticeSession> getOngoingSessionByStudentId(Long userID) {
        return practiceSessionRepository.findByUserUserIDAndSessionStatus(userID,
                PracticeSession.SessionStatus.ONGOING);
    }


    // Start a new session for a student
    @Autowired
    private UserRepository userRepository;

    public PracticeSession startSessionForStudent(Long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        PracticeSession session = new PracticeSession();
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ONGOING);
        session.setStartTime(LocalDateTime.now());
        return practiceSessionRepository.save(session);
    }

    public PracticeSession completeSession(Long sessionId) {
        PracticeSession session = practiceSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setEndTime(LocalDateTime.now());
        session.setSessionStatus(SessionStatus.COMPLETED);

        return practiceSessionRepository.save(session);
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