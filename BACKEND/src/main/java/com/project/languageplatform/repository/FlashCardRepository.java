package com.project.languageplatform.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.enums.DifficultyLevel;

public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    List<FlashCard> findByDifficultyLevel(DifficultyLevel difficultyLevel);

    List<FlashCard> findTop10ByDifficultyLevel(DifficultyLevel difficultyLevel);

    List<FlashCard> findTop10ByOrderByFlashCardIDAsc();

    @Query("SELECT f FROM FlashCard f WHERE f.flashCardID NOT IN (" +
            "SELECT psf.flashCard.flashCardID FROM PracticeSessionFlashCard psf " +
            "WHERE psf.session.user.userID = :userID)")
    List<FlashCard> findUnseenFlashCardsForUser(@Param("userID") Long userID, Pageable pageable);

    boolean existsBySentence(String sentence);
}