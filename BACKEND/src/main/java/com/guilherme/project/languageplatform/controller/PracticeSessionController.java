package com.guilherme.project.languageplatform.controller;

import com.guilherme.project.languageplatform.entity.PracticeSession;
import com.guilherme.project.languageplatform.service.PracticeSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/practice-sessions")
public class PracticeSessionController {

    @Autowired
    private PracticeSessionService practiceSessionService;

    @GetMapping
    public List<PracticeSession> getAllSessions() {
        return practiceSessionService.getAllPracticeSessions();
    }

    @GetMapping("/{id}")
    public Optional<PracticeSession> getSessionById(@PathVariable Long id) {
        return practiceSessionService.getPracticeSessionById(id);
    }

    @GetMapping("/ongoing/{studentId}")
    public ResponseEntity<PracticeSession> getOngoingSession(@PathVariable Long studentId) {
        return practiceSessionService.getOngoingSessionByStudentId(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PracticeSession createSession(@RequestBody PracticeSession session) {
        return practiceSessionService.savePracticeSession(session);
    }

    @PutMapping("/{id}")
    public PracticeSession updateSession(@PathVariable Long id, @RequestBody PracticeSession updatedSession) {
        return practiceSessionService.updatePracticeSession(id, updatedSession);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        practiceSessionService.deletePracticeSession(id);
    }
}