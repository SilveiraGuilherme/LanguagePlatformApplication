package com.project.languageplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.languageplatform.entity.PracticeSession;
import com.project.languageplatform.service.PracticeSessionService;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/practice-sessions")
public class PracticeSessionController {

    @Autowired
    private PracticeSessionService practiceSessionService;

    // Retrieve all practice sessions
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    @GetMapping
    public List<PracticeSession> getAllSessions() {
        return practiceSessionService.getAllPracticeSessions();
    }

    // Retrieve a practice session by its ID
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PracticeSession> getSessionById(@PathVariable Long id) {
        return practiceSessionService.getPracticeSessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Retrieve the ongoing (not finished) practice session for a specific student
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    @GetMapping("/ongoing/{userID}")
    public ResponseEntity<PracticeSession> getOngoingSession(@PathVariable Long userID) {
        return practiceSessionService.getOngoingSessionByStudentId(userID)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new practice session
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    @PostMapping("/start")
    public ResponseEntity<PracticeSession> createSession(@RequestBody Map<String, Long> request) {
        Long userID = request.get("userID");
        PracticeSession session = practiceSessionService.startSessionForStudent(userID);
        return ResponseEntity.status(201).body(session);
    }

    // Update an existing practice session by its ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public PracticeSession updateSession(@PathVariable Long id, @RequestBody PracticeSession updatedSession) {
        return practiceSessionService.updatePracticeSession(id, updatedSession);
    }

    // Delete a practice session by its ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        practiceSessionService.deletePracticeSession(id);
    }
}