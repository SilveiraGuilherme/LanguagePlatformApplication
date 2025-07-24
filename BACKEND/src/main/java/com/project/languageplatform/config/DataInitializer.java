package com.project.languageplatform.config;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.enums.DifficultyLevel;
import com.project.languageplatform.repository.FlashCardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedFlashCards(FlashCardRepository flashCardRepository) {
        return args -> {
            try {
                if (flashCardRepository.count() == 0) {
                    flashCardRepository.save(new FlashCard(
                            "Je suis étudiant", DifficultyLevel.BEGINNER,
                            "I am a teacher", "I am a student", "I am tired", "I am happy",
                            "I am a student"
                    ));
                    flashCardRepository.save(new FlashCard(
                            "Il fait froid aujourd'hui", DifficultyLevel.INTERMEDIATE,
                            "It is raining", "It is warm today", "It is cold today", "It is sunny",
                            "It is cold today"
                    ));
                    flashCardRepository.save(new FlashCard(
                        "Comment tu t'appelles ?", DifficultyLevel.BEGINNER,
                        "How are you?", "What's your name?", "Where do you live?", "How old are you?",
                        "What's your name?"
                    ));
                    flashCardRepository.save(new FlashCard(
                        "J'aime le chocolat", DifficultyLevel.BEGINNER,
                        "I like coffee", "I love pizza", "I like chocolate", "I like ice cream",
                        "I like chocolate"
                    ));
                    flashCardRepository.save(new FlashCard(
                        "Nous allons au cinéma", DifficultyLevel.INTERMEDIATE,
                        "We are going home", "We are going to the cinema", "We are at school", "We are walking",
                        "We are going to the cinema"
                    ));
                    flashCardRepository.save(new FlashCard(
                        "Elle lit un livre", DifficultyLevel.BEGINNER,
                        "She reads a book", "She eats a cake", "She writes a letter", "She sees a movie",
                        "She reads a book"
                    ));
                    flashCardRepository.save(new FlashCard(
                        "Où est la bibliothèque ?", DifficultyLevel.INTERMEDIATE,
                        "Where is the park?", "Where is the restaurant?", "Where is the library?", "Where is the bank?",
                        "Where is the library?"
                    ));
                    flashCardRepository.save(new FlashCard(
                        "Je ne comprends pas", DifficultyLevel.INTERMEDIATE,
                        "I don't know", "I don't remember", "I don't understand", "I can't hear",
                        "I don't understand"
                    ));
                    flashCardRepository.save(new FlashCard(
                        "Ils jouent au football", DifficultyLevel.INTERMEDIATE,
                        "They play tennis", "They play football", "They watch TV", "They run",
                        "They play football"
                    ));
                    flashCardRepository.save(new FlashCard(
                        "Je suis fatigué", DifficultyLevel.BEGINNER,
                        "I am happy", "I am tired", "I am sick", "I am fine",
                        "I am tired"
                    ));
                    System.out.println("✅ Flashcards seeded.");
                } else {
                    System.out.println("ℹ️ Flashcards already exist. No action taken.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error while seeding flashcards: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}