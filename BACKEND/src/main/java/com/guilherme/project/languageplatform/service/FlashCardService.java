package com.guilherme.project.languageplatform.service;

import com.guilherme.project.languageplatform.entity.FlashCard;
import com.guilherme.project.languageplatform.enums.DifficultyLevel;
import com.guilherme.project.languageplatform.repository.FlashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlashCardService {

    @Autowired
    private FlashCardRepository flashCardRepository;

    public List<FlashCard> getAllFlashCards() {
        return flashCardRepository.findAll();
    }

    public Optional<FlashCard> getFlashCardById(Long id) {
        return flashCardRepository.findById(id);
    }

    public List<FlashCard> getFlashCardsByDifficulty(DifficultyLevel difficultyLevel) {
        return flashCardRepository.findByDifficultyLevel(difficultyLevel);
    }

    public FlashCard saveFlashCard(FlashCard flashCard) {
        return flashCardRepository.save(flashCard);
    }

    public void deleteFlashCard(Long id) {
        flashCardRepository.deleteById(id);
    }
}