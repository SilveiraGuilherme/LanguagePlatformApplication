package com.guilherme.project.languageplatform.service;

import com.guilherme.project.languageplatform.entity.PracticeSession;
import com.guilherme.project.languageplatform.entity.Student;
import com.guilherme.project.languageplatform.repository.PracticeSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PracticeSessionService {

    @Autowired
    private PracticeSessionRepository practiceSessionRepository;

    // Retrieve all practice sessions
    public List<PracticeSession> getAllPracticeSessions() {
        return practiceSessionRepository.findAll();
    }

    // Retrieve a practice session by its ID
    public Optional<PracticeSession> getPracticeSessionById(Long id) {
        return practiceSessionRepository.findById(id);
    }

    // Retrieve an ongoing session for a specific student
    public Optional<PracticeSession> getOngoingSessionByStudentId(Long studentId) {
        return practiceSessionRepository.findByStudentStudentIDAndSessionStatus(
                studentId, PracticeSession.SessionStatus.ONGOING);
    }

    // Save a new or updated practice session
    public PracticeSession savePracticeSession(PracticeSession session) {
        return practiceSessionRepository.save(session);
    }

    // Update a session's status and end time
    public PracticeSession updatePracticeSession(Long id, PracticeSession updatedSession) {
        PracticeSession session = practiceSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setSessionStatus(updatedSession.getSessionStatus());
        session.setEndTime(updatedSession.getEndTime());
        return practiceSessionRepository.save(session);
    }

    // Delete a session by its ID
    public void deletePracticeSession(Long id) {
        practiceSessionRepository.deleteById(id);
    }

    // Start a new practice session for a student
    public PracticeSession startNewSession(Long studentId) {
        PracticeSession session = new PracticeSession();
        session.setStudent(new Student(studentId));
        session.setStartTime(LocalDateTime.now());
        session.setSessionStatus(PracticeSession.SessionStatus.ONGOING);
        return practiceSessionRepository.save(session);
    }

    // Mark a session as completed
    public PracticeSession endSession(Long sessionId) {
        PracticeSession session = practiceSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setSessionStatus(PracticeSession.SessionStatus.COMPLETED);
        session.setEndTime(LocalDateTime.now());
        return practiceSessionRepository.save(session);
    }

    // Check if a student currently has an ongoing session
    public boolean hasOngoingSession(Long studentId) {
        return practiceSessionRepository.findByStudentStudentIDAndSessionStatus(
                studentId, PracticeSession.SessionStatus.ONGOING).isPresent();
    }
}