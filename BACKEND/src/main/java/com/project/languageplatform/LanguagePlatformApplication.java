package com.project.languageplatform;

import com.project.languageplatform.entity.FlashCard;
import com.project.languageplatform.repository.FlashCardRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.util.List;

/**
 * Main application class for the Language Platform.
 * It bootstraps the Spring Boot application and loads initial flashcard data.
 */
@SpringBootApplication
public class LanguagePlatformApplication {

	// Entry point for the Spring Boot application
	public static void main(String[] args) {
		SpringApplication.run(LanguagePlatformApplication.class, args);
	}

	// Loads flashcards from a JSON file into the database at application startup
	@Bean
	public CommandLineRunner loadFlashCards(FlashCardRepository repository) {
	    return args -> {
	        ObjectMapper mapper = new ObjectMapper();
	        ClassPathResource resource = new ClassPathResource("flashcards.json");
	        try (InputStream inputStream = resource.getInputStream()) {
	            List<FlashCard> flashCards = mapper.readValue(inputStream, new TypeReference<List<FlashCard>>() {});
	            for (FlashCard flashCard : flashCards) {
	                if (!repository.existsBySentence(flashCard.getSentence())) {
	                    repository.save(flashCard);
	                }
	            }
	        } catch (Exception e) {
	            System.err.println("Failed to load flashcards: " + e.getMessage());
	        }
	    };
	}
}
