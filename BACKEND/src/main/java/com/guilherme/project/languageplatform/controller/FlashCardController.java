package com.guilherme.project.languageplatform.controller;

import com.guilherme.project.languageplatform.entity.FlashCard;
import com.guilherme.project.languageplatform.service.FlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flashcards")
public class FlashCardController {

    @Autowired
    private FlashCardService flashCardService;

    @GetMapping
    public List<FlashCard> getAllFlashCards() {
        return flashCardService.getAllFlashCards();
    }

    @GetMapping("/{id}")
    public Optional<FlashCard> getFlashCardById(@PathVariable Long id) {
        return flashCardService.getFlashCardById(id);
    }

    @PostMapping
    public FlashCard createFlashCard(@RequestBody FlashCard flashCard) {
        return flashCardService.saveFlashCard(flashCard);
    }

    @PutMapping("/{id}")
    public FlashCard updateFlashCard(@PathVariable Long id, @RequestBody FlashCard flashCard) {
        flashCard.setFlashCardID(id);
        return flashCardService.saveFlashCard(flashCard);
    }

    @DeleteMapping("/{id}")
    public void deleteFlashCard(@PathVariable Long id) {
        flashCardService.deleteFlashCard(id);
    }
}