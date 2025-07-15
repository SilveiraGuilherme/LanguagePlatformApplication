package com.guilherme.project.languageplatform.controller;

import com.guilherme.project.languageplatform.entity.PracticeSessionFlashCard;
import com.guilherme.project.languageplatform.entity.id.PracticeSessionFlashCardId;
import com.guilherme.project.languageplatform.enums.Rating;
import com.guilherme.project.languageplatform.service.PracticeSessionFlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/practice-session-flashcards")
public class PracticeSessionFlashCardController {

    @Autowired
    private PracticeSessionFlashCardService service;

    // Retrieve all PracticeSessionFlashCard records
    @GetMapping
    public List<PracticeSessionFlashCard> getAll() {
        return service.getAll();
    }

    // Retrieve a PracticeSessionFlashCard by its composite ID (sessionId and flashCardId)
    @GetMapping("/{sessionId}/{flashCardId}")
    public Optional<PracticeSessionFlashCard> getById(@PathVariable Long sessionId,
            @PathVariable Long flashCardId) {
        PracticeSessionFlashCardId id = new PracticeSessionFlashCardId(sessionId, flashCardId);
        return service.getById(id);
    }

    // Retrieve all flashcards associated with a specific practice session
    @GetMapping("/session/{sessionId}")
    public List<PracticeSessionFlashCard> getBySessionId(@PathVariable Long sessionId) {
        return service.getBySessionId(sessionId);
    }

    // Create a new PracticeSessionFlashCard entry
    @PostMapping
    public PracticeSessionFlashCard create(@RequestBody PracticeSessionFlashCard psfc) {
        return service.save(psfc);
    }

    // Update the rating for a specific PracticeSessionFlashCard
    @PutMapping("/{sessionId}/{flashCardId}/rating")
    public PracticeSessionFlashCard updateRating(@PathVariable Long sessionId,
            @PathVariable Long flashCardId,
            @RequestBody java.util.Map<String, String> request) {
        Rating rating = Rating.valueOf(request.get("rating"));
        PracticeSessionFlashCardId id = new PracticeSessionFlashCardId(sessionId, flashCardId);
        return service.updateRating(id, rating);
    }

    // Delete a PracticeSessionFlashCard by its composite ID
    @DeleteMapping("/{sessionId}/{flashCardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long sessionId,
            @PathVariable Long flashCardId) {
        PracticeSessionFlashCardId id = new PracticeSessionFlashCardId(sessionId, flashCardId);
        service.deleteById(id);
    }
    // Create a PracticeSessionFlashCard from session and flashcard IDs
    @PostMapping("/create")
    public PracticeSessionFlashCard createSimplified(@RequestBody java.util.Map<String, Object> data) {
        Long sessionId = Long.valueOf(data.get("practiceSessionId").toString());
        Long flashCardId = Long.valueOf(data.get("flashCardId").toString());
        Rating rating = Rating.valueOf(data.get("rating").toString());
        return service.createFromIds(sessionId, flashCardId, rating);
    }
    // Get prioritized flashcards for a session
    @GetMapping("/session/{sessionId}/prioritized")
    public List<com.guilherme.project.languageplatform.entity.FlashCard> getPrioritizedFlashCards(
            @PathVariable Long sessionId,
            @RequestParam(defaultValue = "10") int limit) {
        return service.getPrioritizedFlashCardsForSession(sessionId, limit);
    }
}