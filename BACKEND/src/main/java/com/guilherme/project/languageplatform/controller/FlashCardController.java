package com.guilherme.project.languageplatform.controller;

import com.guilherme.project.languageplatform.entity.FlashCard;
import com.guilherme.project.languageplatform.service.FlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flashcards")
public class FlashCardController {

    @Autowired
    private FlashCardService flashCardService;

    // Get all flashcards
    @GetMapping
    public ResponseEntity<List<FlashCard>> getAllFlashCards() {
        return ResponseEntity.ok(flashCardService.getAllFlashCards());
    }

    // Get a flashcard by its ID
    @GetMapping("/{id}")
    public ResponseEntity<FlashCard> getFlashCardById(@PathVariable Long id) {
        Optional<FlashCard> flashCard = flashCardService.getFlashCardById(id);
        return flashCard.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Filter flashcards by difficulty level (e.g., BEGINNER, INTERMEDIATE, ADVANCED)
    @GetMapping("/filter")
    public ResponseEntity<List<FlashCard>> getFlashCardsByDifficulty(@RequestParam String difficultyLevel) {
        if (difficultyLevel == null || difficultyLevel.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(flashCardService.getFlashCardsFiltered(difficultyLevel));
    }

    // Create a new flashcard
    @PostMapping
    public ResponseEntity<FlashCard> createFlashCard(@RequestBody FlashCard flashCard) {
        return ResponseEntity.ok(flashCardService.saveFlashCard(flashCard));
    }

    // Update an existing flashcard by ID
    @PutMapping("/{id}")
    public ResponseEntity<FlashCard> updateFlashCard(@PathVariable Long id, @RequestBody FlashCard flashCard) {
        flashCard.setFlashCardID(id);
        return ResponseEntity.ok(flashCardService.saveFlashCard(flashCard));
    }

    // Delete a flashcard by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlashCard(@PathVariable Long id) {
        flashCardService.deleteFlashCard(id);
        return ResponseEntity.noContent().build();
    }
}