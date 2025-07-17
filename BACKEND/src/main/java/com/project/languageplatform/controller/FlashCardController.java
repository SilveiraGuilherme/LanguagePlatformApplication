package com.project.languageplatform.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.service.FlashCardService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flashcards")
public class FlashCardController {

    @Autowired
    private FlashCardService flashCardService;

    // Get all flashcards
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<FlashCard>> getAllFlashCards() {
        return ResponseEntity.ok(flashCardService.getAllFlashCards());
    }

    // Get a flashcard by its ID
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<FlashCard> getFlashCardById(@PathVariable Long id) {
        Optional<FlashCard> flashCard = flashCardService.getFlashCardById(id);
        return flashCard.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Filter flashcards by difficulty level (e.g., BEGINNER, INTERMEDIATE, ADVANCED)
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<List<FlashCard>> getFlashCardsByDifficulty(@RequestParam String difficultyLevel) {
        if (difficultyLevel == null || difficultyLevel.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        List<String> allowed = List.of("BEGINNER", "INTERMEDIATE", "ADVANCED");
        if (!allowed.contains(difficultyLevel.toUpperCase())) {
            return ResponseEntity.badRequest().body(List.of());
        }

        return ResponseEntity.ok(flashCardService.getFlashCardsFiltered(difficultyLevel.toUpperCase()));
    }

    // Create a new flashcard
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FlashCard> createFlashCard(@RequestBody FlashCard flashCard) {
        return ResponseEntity.ok(flashCardService.saveFlashCard(flashCard));
    }

    // Update an existing flashcard by ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FlashCard> updateFlashCard(@PathVariable Long id, @RequestBody FlashCard flashCard) {
        flashCard.setFlashCardID(id);
        return ResponseEntity.ok(flashCardService.saveFlashCard(flashCard));
    }

    // Delete a flashcard by ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlashCard(@PathVariable Long id) {
        flashCardService.deleteFlashCard(id);
        return ResponseEntity.noContent().build();
    }
}