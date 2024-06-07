package com.vegadelalyra.quizapp.quiz.controller;

import com.vegadelalyra.quizapp.question.model.QuestionWrapper;
import com.vegadelalyra.quizapp.quiz.model.Response;
import com.vegadelalyra.quizapp.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping()
    public ResponseEntity<String> createQuiz(
                @RequestParam String category,
                @RequestParam int numQ,
                @RequestParam String title
            ) {
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
            return quizService.getQuizQuestions(id);
    }

    @PostMapping("{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }
}
