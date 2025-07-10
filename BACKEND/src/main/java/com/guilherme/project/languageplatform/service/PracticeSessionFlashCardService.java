package com.guilherme.project.languageplatform.service;

import com.guilherme.project.languageplatform.entity.PracticeSessionFlashCard;
import com.guilherme.project.languageplatform.entity.id.PracticeSessionFlashCardId;
import com.guilherme.project.languageplatform.enums.Rating;
import com.guilherme.project.languageplatform.repository.PracticeSessionFlashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracticeSessionFlashCardService {

    @Autowired
    private PracticeSessionFlashCardRepository repository;

    public List<PracticeSessionFlashCard> getAll() {
        return repository.findAll();
    }

    public Optional<PracticeSessionFlashCard> getById(PracticeSessionFlashCardId id) {
        return repository.findById(id);
    }

    public PracticeSessionFlashCard save(PracticeSessionFlashCard psfc) {
        return repository.save(psfc);
    }

    public void deleteById(PracticeSessionFlashCardId id) {
        repository.deleteById(id);
    }

    // Get all flashcards for a session
    public List<PracticeSessionFlashCard> getBySessionId(Integer sessionId) {
        return repository.findByIdSessionID(sessionId);
    }

    // Update Rating
    public PracticeSessionFlashCard updateRating(PracticeSessionFlashCardId id, Rating newRating) {
        Optional<PracticeSessionFlashCard> optional = repository.findById(id);
        if (optional.isPresent()) {
            PracticeSessionFlashCard psfc = optional.get();
            psfc.setRating(newRating);
            return repository.save(psfc);
        } else {
            throw new RuntimeException("Flashcard not found in session");
        }
    }
}