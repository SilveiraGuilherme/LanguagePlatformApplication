package com.project.languageplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.languageplatform.entity.PracticeSessionFlashCard;
import com.project.languageplatform.entity.id.PracticeSessionFlashCardId;

public interface PracticeSessionFlashCardRepository
        extends JpaRepository<PracticeSessionFlashCard, PracticeSessionFlashCardId> {

    List<PracticeSessionFlashCard> findBySessionSessionID(Long sessionID);

    List<PracticeSessionFlashCard> findBySessionUserUserID(Long userID);
}