package com.guilherme.project.languageplatform.controller;

import com.guilherme.project.languageplatform.entity.QuizResult;
import com.guilherme.project.languageplatform.service.QuizResultService;
import com.guilherme.project.languageplatform.dto.QuizResultResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz-results")
public class QuizResultController {

    @Autowired
    private QuizResultService quizResultService;

    // Get all quiz results
    @GetMapping
    public List<QuizResultResponseDTO> getAllQuizResults() {
        return quizResultService.getAllQuizResults().stream().map(result -> {
            QuizResultResponseDTO dto = new QuizResultResponseDTO();
            dto.setResultID(result.getResultID());
            dto.setStudentID(result.getStudent().getStudentID());
            dto.setStudentName(result.getStudent().getFirstName() + " " + result.getStudent().getLastName());
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
    public ResponseEntity<QuizResultResponseDTO> getQuizResultById(@PathVariable Long id) {
        QuizResult result = quizResultService.getQuizResultById(id);
        QuizResultResponseDTO dto = new QuizResultResponseDTO();
        dto.setResultID(result.getResultID());
        dto.setStudentID(result.getStudent().getStudentID());
        dto.setStudentName(result.getStudent().getFirstName() + " " + result.getStudent().getLastName());
        dto.setSessionID(result.getSession().getSessionID());
        dto.setDifficultyLevel(result.getDifficultyLevel().toString());
        dto.setTotalQuestions(result.getTotalQuestions());
        dto.setCorrectAnswers(result.getCorrectAnswers());
        dto.setScorePercentage(result.getScorePercentage());
        dto.setCompletionTime(result.getCompletionTime());
        return ResponseEntity.ok(dto);
    }

    // Create a new quiz result
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuizResult createQuizResult(@RequestBody QuizResult quizResult) {
        return quizResultService.saveQuizResult(quizResult);
    }

    // Process quiz submission
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