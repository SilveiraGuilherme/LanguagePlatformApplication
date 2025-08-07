package com.project.languageplatform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.enums.DifficultyLevel;
import com.project.languageplatform.repository.FlashCardRepository;
import com.project.languageplatform.service.FlashCardService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FlashCardServiceTest {

    @Autowired
    private FlashCardService flashCardService;

    @Autowired
    private FlashCardRepository flashCardRepository;

    @BeforeEach
    public void setup() {
        flashCardRepository.deleteAll(); // ensure a clean start

        // Insert a dummy flashcard for testing
        FlashCard flashCard = new FlashCard();
        flashCard.setSentence("Comment tu t'appelles ?");
        flashCard.setCorrectAnswer("What’s your name?");
        flashCard.setOptionA("Where are you?");
        flashCard.setOptionB("What’s your name?");
        flashCard.setOptionC("How old are you?");
        flashCard.setOptionD("What’s your job?");
        flashCard.setDifficultyLevel(DifficultyLevel.BEGINNER);

        flashCardRepository.save(flashCard);
    }

    @Test
    public void testGetAllFlashCards() {
        List<FlashCard> flashCards = flashCardService.getAllFlashCards();
        System.out.println("Test ran successfully. Flashcard count: " + flashCards.size());
        assertFalse(flashCards.isEmpty(), "FlashCards list should not be empty");
        assertEquals(1, flashCards.size(), "There should be 1 flashcard");
        assertEquals("Comment tu t'appelles ?", flashCards.get(0).getSentence());
    }
}