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

    // Retrieve all practice sessions
    @GetMapping
    public List<PracticeSession> getAllSessions() {
        return practiceSessionService.getAllPracticeSessions();
    }

    // Retrieve a practice session by its ID
    @GetMapping("/{id}")
    public ResponseEntity<PracticeSession> getSessionById(@PathVariable Long id) {
        return practiceSessionService.getPracticeSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Retrieve the ongoing (not finished) practice session for a specific student
    @GetMapping("/ongoing/{studentId}")
    public ResponseEntity<PracticeSession> getOngoingSession(@PathVariable Long studentId) {
        return practiceSessionService.getOngoingSessionByStudentId(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new practice session
    @PostMapping
    public ResponseEntity<PracticeSession> createSession(@RequestBody PracticeSession session) {
        PracticeSession saved = practiceSessionService.savePracticeSession(session);
        return ResponseEntity.status(201).body(saved);
    }

    // Update an existing practice session by its ID
    @PutMapping("/{id}")
    public PracticeSession updateSession(@PathVariable Long id, @RequestBody PracticeSession updatedSession) {
        return practiceSessionService.updatePracticeSession(id, updatedSession);
    }

    // Delete a practice session by its ID
    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        practiceSessionService.deletePracticeSession(id);
    }
}