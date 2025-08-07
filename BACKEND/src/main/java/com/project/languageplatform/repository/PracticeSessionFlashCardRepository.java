package com.project.languageplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.languageplatform.entity.PracticeSessionFlashCard;
import com.project.languageplatform.entity.id.PracticeSessionFlashCardId;

/**
 * Repository for managing PracticeSessionFlashCard entities.
 * Provides methods to fetch flashcards by session or user.
 */
public interface PracticeSessionFlashCardRepository
        extends JpaRepository<PracticeSessionFlashCard, PracticeSessionFlashCardId> {

    // Find flashcards associated with a specific practice session
    List<PracticeSessionFlashCard> findBySessionSessionID(Long sessionID);

    // Find all flashcards associated with sessions of a specific user
    List<PracticeSessionFlashCard> findBySessionUserUserID(Long userID);
}