package com.project.languageplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.enums.DifficultyLevel;
import com.project.languageplatform.repository.FlashCardRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for managing flashcards.
 * It interacts with the FlashCardRepository to perform CRUD operations.
 */
@Service
public class FlashCardService {

    @Autowired
    private FlashCardRepository flashCardRepository;

    /**
     * Constructor for FlashCardService.
     * @param flashCardRepository the repository to interact with flashcards.
     */
    public FlashCardService(FlashCardRepository flashCardRepository) {
        this.flashCardRepository = flashCardRepository;
    }

    // Get all flashcards
    public List<FlashCard> getAllFlashCards() {
        return flashCardRepository.findAll();
    }

    // Get a flashcard by ID
    public Optional<FlashCard> getFlashCardById(Long id) {
        return flashCardRepository.findById(id);
    }

    // Get flashcards by enum difficulty
    public List<FlashCard> getFlashCardsByDifficulty(DifficultyLevel difficultyLevel) {
        return flashCardRepository.findByDifficultyLevel(difficultyLevel);
    }

    // Get flashcards by difficulty string or all if invalid
    public List<FlashCard> getFlashCardsFiltered(String difficultyLevelStr) {
        try {
            DifficultyLevel difficultyLevel = DifficultyLevel.valueOf(difficultyLevelStr.toUpperCase());
            return flashCardRepository.findByDifficultyLevel(difficultyLevel);
        } catch (IllegalArgumentException e) {
            return flashCardRepository.findAll();
        }
    }

    // Save or update a flashcard
    public FlashCard saveFlashCard(FlashCard flashCard) {
        return flashCardRepository.save(flashCard);
    }

    // Delete a flashcard by ID
    public void deleteFlashCard(Long id) {
        flashCardRepository.deleteById(id);
    }
}