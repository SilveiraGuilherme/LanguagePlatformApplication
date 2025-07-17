package com.project.languageplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.enums.DifficultyLevel;

public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    List<FlashCard> findByDifficultyLevel(DifficultyLevel difficultyLevel);
}