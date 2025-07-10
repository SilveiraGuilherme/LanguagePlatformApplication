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

    public List<PracticeSession> getAllPracticeSessions() {
        return practiceSessionRepository.findAll();
    }

    public Optional<PracticeSession> getPracticeSessionById(Integer id) {
        return practiceSessionRepository.findById(id);
    }

    public Optional<PracticeSession> getOngoingSessionByStudentId(Integer studentId) {
        return practiceSessionRepository.findByStudentStudentIDAndSessionStatus(studentId,
                PracticeSession.SessionStatus.ONGOING);
    }

    public PracticeSession savePracticeSession(PracticeSession session) {
        return practiceSessionRepository.save(session);
    }

    public PracticeSession updatePracticeSession(Integer id, PracticeSession updatedSession) {
        PracticeSession session = practiceSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setSessionStatus(updatedSession.getSessionStatus());
        session.setEndTime(updatedSession.getEndTime());

        return practiceSessionRepository.save(session);
    }

    public void deletePracticeSession(Integer id) {
        practiceSessionRepository.deleteById(id);
    }
}