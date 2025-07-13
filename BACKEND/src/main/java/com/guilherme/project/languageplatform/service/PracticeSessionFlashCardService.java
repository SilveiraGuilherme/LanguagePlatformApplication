package com.guilherme.project.languageplatform.service;

import com.guilherme.project.languageplatform.entity.FlashCard;
import com.guilherme.project.languageplatform.entity.PracticeSession;
import com.guilherme.project.languageplatform.entity.PracticeSessionFlashCard;
import com.guilherme.project.languageplatform.entity.id.PracticeSessionFlashCardId;
import com.guilherme.project.languageplatform.enums.Rating;
import com.guilherme.project.languageplatform.repository.PracticeSessionFlashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PracticeSessionFlashCardService {

    @Autowired
    private PracticeSessionFlashCardRepository repository;

    // Get all PracticeSessionFlashCards
    public List<PracticeSessionFlashCard> getAll() {
        return repository.findAll();
    }

    // Get one by composite ID
    public Optional<PracticeSessionFlashCard> getById(PracticeSessionFlashCardId id) {
        return repository.findById(id);
    }

    // Save or update a PracticeSessionFlashCard
    public PracticeSessionFlashCard save(PracticeSessionFlashCard psfc) {
        return repository.save(psfc);
    }

    // Delete by ID
    public void deleteById(PracticeSessionFlashCardId id) {
        repository.deleteById(id);
    }

    // Get all flashcards in a session
    public List<PracticeSessionFlashCard> getBySessionId(Long sessionId) {
        return repository.findByIdSessionID(sessionId);
    }

    // Update rating for a flashcard in session
    public PracticeSessionFlashCard updateRating(PracticeSessionFlashCardId id, Rating newRating) {
        Optional<PracticeSessionFlashCard> optional = repository.findById(id);
        if (optional.isPresent()) {
            PracticeSessionFlashCard psfc = optional.get();
            psfc.setRating(newRating);
            return repository.save(psfc);
        } else {
            throw new EntityNotFoundException("Flashcard not found in session");
        }
    }

    public PracticeSessionFlashCard addFlashCardToSession(PracticeSession session, FlashCard flashCard) {
        PracticeSessionFlashCard psfc = new PracticeSessionFlashCard(session, flashCard);
        return repository.save(psfc);
    }

    // Get flashcards for student sorted by priority (DONT_KNOW > HARD > ...)
    public List<FlashCard> getPrioritizedFlashCardsForStudent(Long studentId, int maxFlashcards) {
        List<PracticeSessionFlashCard> rated = repository.findByIdStudentID(studentId);

        // Sort by rating priority
        rated.sort((a, b) -> Integer.compare(getPriority(b.getRating()), getPriority(a.getRating())));

        return rated.stream()
                    .limit(maxFlashcards)
                    .map(PracticeSessionFlashCard::getFlashCard)
                    .toList();
    }

    private int getPriority(Rating rating) {
        return switch (rating) {
            case DONT_KNOW -> 4;
            case HARD -> 3;
            case MEDIUM -> 2;
            case EASY -> 1;
            default -> 1;
        };
    }
}