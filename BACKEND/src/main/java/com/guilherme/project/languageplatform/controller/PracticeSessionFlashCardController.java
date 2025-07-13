package com.guilherme.project.languageplatform.controller;

import com.guilherme.project.languageplatform.entity.PracticeSessionFlashCard;
import com.guilherme.project.languageplatform.entity.id.PracticeSessionFlashCardId;
import com.guilherme.project.languageplatform.enums.Rating;
import com.guilherme.project.languageplatform.service.PracticeSessionFlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/practice-session-flashcards")
public class PracticeSessionFlashCardController {

    @Autowired
    private PracticeSessionFlashCardService service;

    // Get all PracticeSessionFlashCard
    @GetMapping
    public List<PracticeSessionFlashCard> getAll() {
        return service.getAll();
    }

    // Get by composite ID
    @GetMapping("/{sessionId}/{flashCardId}")
    public Optional<PracticeSessionFlashCard> getById(@PathVariable Long sessionId,
            @PathVariable Long flashCardId) {
        PracticeSessionFlashCardId id = new PracticeSessionFlashCardId(sessionId, flashCardId);
        return service.getById(id);
    }

    // Get all flashcards for a session
    @GetMapping("/session/{sessionId}")
    public List<PracticeSessionFlashCard> getBySessionId(@PathVariable Integer sessionId) {
        return service.getBySessionId(sessionId);
    }

    // Create a new PracticeSessionFlashCard
    @PostMapping
    public PracticeSessionFlashCard create(@RequestBody PracticeSessionFlashCard psfc) {
        return service.save(psfc);
    }

    // Update rating
    @PutMapping("/{sessionId}/{flashCardId}/rating")
    public PracticeSessionFlashCard updateRating(@PathVariable Long sessionId,
            @PathVariable Long flashCardId,
            @RequestParam Rating rating) {
        PracticeSessionFlashCardId id = new PracticeSessionFlashCardId(sessionId, flashCardId);
        return service.updateRating(id, rating);
    }

    // Delete by composite ID
    @DeleteMapping("/{sessionId}/{flashCardId}")
    public void delete(@PathVariable Long sessionId,
            @PathVariable Long flashCardId) {
        PracticeSessionFlashCardId id = new PracticeSessionFlashCardId(sessionId, flashCardId);
        service.deleteById(id);
    }
}