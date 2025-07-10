package com.guilherme.project.languageplatform.controller;

import com.guilherme.project.languageplatform.entity.QuizResult;
import com.guilherme.project.languageplatform.service.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz-results")
public class QuizResultController {

    @Autowired
    private QuizResultService quizResultService;

    @GetMapping
    public List<QuizResult> getAllQuizResults() {
        return quizResultService.getAllQuizResults();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResult> getQuizResultById(@PathVariable Long id) {
        Optional<QuizResult> result = quizResultService.getQuizResultById(id);
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public QuizResult createQuizResult(@RequestBody QuizResult quizResult) {
        return quizResultService.saveQuizResult(quizResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizResult> updateQuizResult(@PathVariable Long id, @RequestBody QuizResult updatedResult) {
        Optional<QuizResult> existing = quizResultService.getQuizResultById(id);
        if (existing.isPresent()) {
            updatedResult.setResultID(id);
            return ResponseEntity.ok(quizResultService.saveQuizResult(updatedResult));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizResult(@PathVariable Long id) {
        Optional<QuizResult> existing = quizResultService.getQuizResultById(id);
        if (existing.isPresent()) {
            quizResultService.deleteQuizResult(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}