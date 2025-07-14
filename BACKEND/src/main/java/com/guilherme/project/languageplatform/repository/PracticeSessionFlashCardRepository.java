package com.guilherme.project.languageplatform.repository;

import com.guilherme.project.languageplatform.entity.PracticeSessionFlashCard;
import com.guilherme.project.languageplatform.entity.id.PracticeSessionFlashCardId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeSessionFlashCardRepository
        extends JpaRepository<PracticeSessionFlashCard, PracticeSessionFlashCardId> {

    List<PracticeSessionFlashCard> findBySessionSessionID(Long sessionID);

    List<PracticeSessionFlashCard> findBySessionStudentStudentID(Long studentId);
}