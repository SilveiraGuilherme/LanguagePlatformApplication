package com.project.languageplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.languageplatform.dto.FlashCardResponseDTO;
import com.project.languageplatform.entity.PracticeSessionFlashCard;
import com.project.languageplatform.entity.id.PracticeSessionFlashCardId;
import com.project.languageplatform.enums.Rating;
import com.project.languageplatform.service.PracticeSessionFlashCardService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/practice-session-flashcards")
@PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
public class PracticeSessionFlashCardController {

    @Autowired
    private PracticeSessionFlashCardService service;

    // Retrieve all PracticeSessionFlashCard records
    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public List<PracticeSessionFlashCard> getAll() {
        return service.getAll();
    }

    // Retrieve a PracticeSessionFlashCard by its composite ID (sessionId and
    // flashCardId)
    @GetMapping("/{sessionId}/{flashCardId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public Optional<PracticeSessionFlashCard> getById(@PathVariable Long sessionId,
            @PathVariable Long flashCardId) {
        PracticeSessionFlashCardId id = new PracticeSessionFlashCardId(sessionId, flashCardId);
        return service.getById(id);
    }

    // Retrieve all flashcards associated with a specific practice session
    @GetMapping("/session/{sessionId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public List<PracticeSessionFlashCard> getBySessionId(@PathVariable Long sessionId) {
        return service.getBySessionId(sessionId);
    }

    // Create a new PracticeSessionFlashCard entry
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PracticeSessionFlashCard create(@RequestBody PracticeSessionFlashCard psfc) {
        return service.save(psfc);
    }

    // Update the rating for a specific PracticeSessionFlashCard
    @PutMapping("/{sessionId}/{flashCardId}/rating")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public void updateRating(@PathVariable Long sessionId,
            @PathVariable Long flashCardId,
            @RequestBody java.util.Map<String, String> request) {
        Rating rating = Rating.valueOf(request.get("rating"));
        Long userId = Long.valueOf(request.get("userID"));

        service.upsertRating(sessionId, flashCardId, userId, rating);
    }

    // Delete a PracticeSessionFlashCard by its composite ID
    @DeleteMapping("/{sessionId}/{flashCardId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long sessionId,
            @PathVariable Long flashCardId) {
        PracticeSessionFlashCardId id = new PracticeSessionFlashCardId(sessionId, flashCardId);
        service.deleteById(id);
    }

    // Create a PracticeSessionFlashCard from session and flashcard IDs
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public PracticeSessionFlashCard createSimplified(@RequestBody java.util.Map<String, Object> data) {
        Long sessionId = Long.valueOf(data.get("practiceSessionId").toString());
        Long flashCardId = Long.valueOf(data.get("flashCardId").toString());
        Rating rating = Rating.valueOf(data.get("rating").toString());
        return service.createFromIds(sessionId, flashCardId, rating);
    }

    // Get prioritized flashcards for a session
    @GetMapping("/session/{sessionId}/prioritized")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public List<com.project.languageplatform.entity.FlashCard> getPrioritizedFlashCards(
            @PathVariable Long sessionId,
            @RequestParam(defaultValue = "10") int limit) {
        return service.getPrioritizedFlashCardsForSession(sessionId, limit);
    }

    // Get next flashcards for a user (prioritized, filled with fresh if needed)
    @GetMapping("/next-flashcards/{userID}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public List<FlashCardResponseDTO> getNextSessionFlashCardsForUser(
            @PathVariable Long userID,
            @RequestParam(defaultValue = "10") int limit) {
        return service.getNextSessionFlashCards(userID, limit)
                .stream()
                .map(FlashCardResponseDTO::new)
                .collect(Collectors.toList());
    }
}