package com.guilherme.project.languageplatform.repository;

import com.guilherme.project.languageplatform.entity.FlashCard;
import com.guilherme.project.languageplatform.enums.DifficultyLevel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    List<FlashCard> findByDifficultyLevel(DifficultyLevel difficultyLevel);
}