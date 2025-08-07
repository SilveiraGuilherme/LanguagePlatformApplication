package com.project.languageplatform.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.enums.DifficultyLevel;

public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {

    // Find all flashcards with the given difficulty level
    List<FlashCard> findByDifficultyLevel(DifficultyLevel difficultyLevel);

    // Find top 10 flashcards with the given difficulty level
    List<FlashCard> findTop10ByDifficultyLevel(DifficultyLevel difficultyLevel);

    // Find the first 10 flashcards ordered by ID ascending
    List<FlashCard> findTop10ByOrderByFlashCardIDAsc();

    // Find flashcards the user has never seen before (not practiced in any session)
    @Query("SELECT f FROM FlashCard f WHERE f.flashCardID NOT IN (" +
           "SELECT psf.flashCard.flashCardID FROM PracticeSessionFlashCard psf " +
           "WHERE psf.session.user.userID = :userID)")
    List<FlashCard> findUnseenFlashCardsForUser(@Param("userID") Long userID, Pageable pageable);

    // Check if a flashcard already exists by sentence content
    boolean existsBySentence(String sentence);
}