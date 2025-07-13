package com.guilherme.project.languageplatform.controller;

import com.guilherme.project.languageplatform.entity.QuizResult;
import com.guilherme.project.languageplatform.service.QuizResultService;
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
    public List<QuizResult> getAllQuizResults() {
        return quizResultService.getAllQuizResults();
    }

    // Get a quiz result by ID
    @GetMapping("/{id}")
    public ResponseEntity<QuizResult> getQuizResultById(@PathVariable Long id) {
        QuizResult result = quizResultService.getQuizResultById(id);
        return ResponseEntity.ok(result);
    }

    // Create a new quiz result
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuizResult createQuizResult(@RequestBody QuizResult quizResult) {
        return quizResultService.saveQuizResult(quizResult);
    }

    // Submit a completed quiz for processing
    @PostMapping("/submit")
    public ResponseEntity<QuizResult> submitQuiz(@RequestBody Map<String, Object> submissionData) {
        QuizResult result = quizResultService.processQuizSubmission(submissionData);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
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