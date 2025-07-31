package com.project.languageplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.languageplatform.dto.QuizResultResponseDTO;
import com.project.languageplatform.entity.QuizResult;
import com.project.languageplatform.service.QuizResultService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz-results")
public class QuizResultController {

    @Autowired
    private QuizResultService quizResultService;

    // Get all quiz results
    @GetMapping
    public List<QuizResultResponseDTO> getAllQuizResults() {
        return quizResultService.getAllQuizResults().stream().map(result -> {
            QuizResultResponseDTO dto = new QuizResultResponseDTO();
            dto.setResultID(result.getResultID());
            dto.setStudentID(result.getUser().getUserID());
            dto.setStudentName(result.getUser().getFirstName() + " " + result.getUser().getLastName());
            dto.setSessionID(result.getSession().getSessionID());
            dto.setDifficultyLevel(result.getDifficultyLevel().toString());
            dto.setTotalQuestions(result.getTotalQuestions());
            dto.setCorrectAnswers(result.getCorrectAnswers());
            dto.setScorePercentage(result.getScorePercentage());
            dto.setCompletionTime(result.getCompletionTime());
            return dto;
        }).toList();
    }

    // Get a quiz result by ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<QuizResultResponseDTO> getQuizResultById(@PathVariable Long id) {
        QuizResult result = quizResultService.getQuizResultById(id);
        QuizResultResponseDTO dto = new QuizResultResponseDTO();
        dto.setResultID(result.getResultID());
        dto.setStudentID(result.getUser().getUserID());
        dto.setStudentName(result.getUser().getFirstName() + " " + result.getUser().getLastName());
        dto.setSessionID(result.getSession().getSessionID());
        dto.setDifficultyLevel(result.getDifficultyLevel().toString());
        dto.setTotalQuestions(result.getTotalQuestions());
        dto.setCorrectAnswers(result.getCorrectAnswers());
        dto.setScorePercentage(result.getScorePercentage());
        dto.setCompletionTime(result.getCompletionTime());
        return ResponseEntity.ok(dto);
    }

    // Get all quiz results for a given user (for profile history or user dashboard)
    // Get all quiz results by a specific user ID
    @GetMapping("/user/{userID}")
    public List<QuizResultResponseDTO> getQuizResultsByUserID(@PathVariable Long userID) {
        return quizResultService.getQuizResultsByUserID(userID).stream().map(result -> {
            QuizResultResponseDTO dto = new QuizResultResponseDTO();
            dto.setResultID(result.getResultID());
            dto.setStudentID(result.getUser().getUserID());
            dto.setStudentName(result.getUser().getFirstName() + " " + result.getUser().getLastName());
            dto.setSessionID(result.getSession().getSessionID());
            dto.setDifficultyLevel(result.getDifficultyLevel().toString());
            dto.setTotalQuestions(result.getTotalQuestions());
            dto.setCorrectAnswers(result.getCorrectAnswers());
            dto.setScorePercentage(result.getScorePercentage());
            dto.setCompletionTime(result.getCompletionTime());
            return dto;
        }).toList();
    }

    // Create multiple quiz results
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<QuizResult>> createMultipleQuizResults(@RequestBody List<QuizResult> quizResults) {
        List<QuizResult> savedResults = quizResults.stream()
                .map(quizResultService::saveQuizResult)
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResults);
    }

    // Process quiz submission
    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @PostMapping("/submit")
    public ResponseEntity<QuizResultResponseDTO> submitQuiz(@RequestBody Map<String, Object> submissionData) {
        QuizResultResponseDTO result = quizResultService.processQuizSubmission(submissionData);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // Update an existing quiz result
    @PutMapping("/{id}")
    public ResponseEntity<QuizResult> updateQuizResult(@PathVariable Long id, @RequestBody QuizResult updatedResult) {
        updatedResult.setResultID(id);
        QuizResult updated = quizResultService.saveQuizResult(updatedResult);
        return ResponseEntity.ok(updated);
    }

    // Delete a quiz result by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizResult(@PathVariable Long id) {
        quizResultService.deleteQuizResultById(id);
        return ResponseEntity.noContent().build();
    }
}