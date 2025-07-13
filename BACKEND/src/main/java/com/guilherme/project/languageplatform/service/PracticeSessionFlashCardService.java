package com.guilherme.project.languageplatform.service;

import com.guilherme.project.languageplatform.entity.FlashCard;
import com.guilherme.project.languageplatform.entity.PracticeSession;
import com.guilherme.project.languageplatform.entity.PracticeSessionFlashCard;
import com.guilherme.project.languageplatform.entity.id.PracticeSessionFlashCardId;
import com.guilherme.project.languageplatform.enums.Rating;
import com.guilherme.project.languageplatform.repository.PracticeSessionFlashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PracticeSessionFlashCardService {

    @Autowired
    private PracticeSessionFlashCardRepository repository;

    /**
     * Get all session-flashcard links.
     *
     * @return a list containing all PracticeSessionFlashCard entities
     */
    public List<PracticeSessionFlashCard> getAll() {
        return repository.findAll();
    }

    /**
     * Find by composite ID.
     *
     * @param id the composite ID of the PracticeSessionFlashCard
     * @return an Optional containing the found PracticeSessionFlashCard, or empty if not found
     */
    public Optional<PracticeSessionFlashCard> getById(PracticeSessionFlashCardId id) {
        return repository.findById(id);
    }

    /**
     * Save or update flashcard in session.
     *
     * @param psfc the PracticeSessionFlashCard to save
     * @return the saved PracticeSessionFlashCard
     */
    public PracticeSessionFlashCard save(PracticeSessionFlashCard psfc) {
        return repository.save(psfc);
    }

    /**
     * Delete link by ID.
     *
     * @param id the composite ID of the PracticeSessionFlashCard to delete
     */
    public void deleteById(PracticeSessionFlashCardId id) {
        repository.deleteById(id);
    }

    /**
     * Get all flashcards for a session.
     *
     * @param sessionId the ID of the practice session
     * @return a list of PracticeSessionFlashCards in the specified session
     */
    public List<PracticeSessionFlashCard> getBySessionId(Integer sessionId) {
        return repository.findByIdSessionID(sessionId);
    }

    /**
     * Update student rating for a flashcard.
     *
     * @param id the composite ID of the PracticeSessionFlashCard to update
     * @param newRating the new rating to set
     * @return the updated PracticeSessionFlashCard
     * @throws EntityNotFoundException if the flashcard is not found in the session
     */
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
}