package com.project.languageplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.context.annotation.Lazy;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.enums.DifficultyLevel;
import com.project.languageplatform.repository.FlashCardRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for handling business logic related to FlashCards.
 * Provides methods to retrieve, filter, save, and delete flashcards.
 */
@Service
public class FlashCardService {

    private final FlashCardRepository flashCardRepository;

    @Autowired
    public FlashCardService(@Lazy FlashCardRepository flashCardRepository) {
        this.flashCardRepository = flashCardRepository;
    }

    // Retrieve all flashcards
    public List<FlashCard> getAllFlashCards() {
        return flashCardRepository.findAll();
    }

    // Retrieve a flashcard by its ID
    public Optional<FlashCard> getFlashCardById(Long id) {
        return flashCardRepository.findById(id);
    }

    // Retrieve flashcards by DifficultyLevel enum
    public List<FlashCard> getFlashCardsByDifficulty(DifficultyLevel difficultyLevel) {
        return flashCardRepository.findByDifficultyLevel(difficultyLevel);
    }

    // Retrieve flashcards by difficulty string or all if input is invalid
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

    // Retrieve unseen flashcards for a specific user with a limit
    public List<FlashCard> getUnseenFlashCards(Long userID, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return flashCardRepository.findUnseenFlashCardsForUser(userID, pageable);
    }
}