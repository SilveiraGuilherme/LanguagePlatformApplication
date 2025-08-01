package com.project.languageplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.entity.PracticeSession;
import com.project.languageplatform.entity.PracticeSessionFlashCard;
import com.project.languageplatform.entity.id.PracticeSessionFlashCardId;
import com.project.languageplatform.enums.Rating;
import com.project.languageplatform.repository.PracticeSessionFlashCardRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PracticeSessionFlashCardService {

    @Autowired
    private PracticeSessionFlashCardRepository repository;

    @Autowired
    private PracticeSessionService practiceSessionService;

    @Autowired
    private FlashCardService flashCardService;

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
        return repository.findBySessionSessionID(sessionId);
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
    public List<FlashCard> getPrioritizedFlashCardsForStudent(Long userID, int maxFlashcards) {
        List<PracticeSessionFlashCard> rated = repository.findBySessionUserUserID(userID);

        // Sort by rating priority
        rated.sort((a, b) -> Integer.compare(getPriority(b.getRating()), getPriority(a.getRating())));

        return rated.stream()
                .limit(maxFlashcards)
                .map(PracticeSessionFlashCard::getFlashCard)
                .toList();
    }

    // Get flashcards for session sorted by priority
    public List<FlashCard> getPrioritizedFlashCardsForSession(Long sessionId, int maxFlashcards) {
        List<PracticeSessionFlashCard> rated = repository.findBySessionSessionID(sessionId);

        rated.sort((a, b) -> Integer.compare(getPriority(b.getRating()), getPriority(a.getRating())));

        return rated.stream()
                .limit(maxFlashcards)
                .map(PracticeSessionFlashCard::getFlashCard)
                .toList();
    }

    // Helper method to get priority based on rating
    private int getPriority(Rating rating) {
        return switch (rating) {
            case DONT_KNOW -> 4;
            case HARD -> 3;
            case MEDIUM -> 2;
            case EASY -> 1;
            default -> 1;
        };
    }

    // Create a PracticeSessionFlashCard from session and flashcard IDs
    public PracticeSessionFlashCard createFromIds(Long sessionId, Long flashCardId, Rating rating) {
        PracticeSession session = practiceSessionService.getPracticeSessionById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("PracticeSession not found with id: " + sessionId));
        FlashCard flashCard = flashCardService.getFlashCardById(flashCardId)
                .orElseThrow(() -> new EntityNotFoundException("FlashCard not found with id: " + flashCardId));

        PracticeSessionFlashCard psfc = new PracticeSessionFlashCard(session, flashCard);
        psfc.setRating(rating);
        // Set a default position (can be ignored if dynamically calculated later)
        psfc.setPositionInQueue(0);
        return repository.save(psfc);
    }
}