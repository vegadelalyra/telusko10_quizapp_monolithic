package com.vegadelalyra.quizapp.quiz.controller;
import com.vegadelalyra.quizapp.quiz.service.QuizService;
import com.vegadelalyra.quizapp.question.model.QuestionWrapper;
import com.vegadelalyra.quizapp.quiz.model.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizControllerTest {

    @Mock
    QuizService quizService;

    @InjectMocks
    QuizController quizController;

    @Test
    void testCreateQuiz() {
        // Arrange
        String category = "Test Category";
        int numQ = 5;
        String title = "Test Quiz";
        ResponseEntity<String> expectedResponseEntity = new ResponseEntity<>("success", HttpStatus.CREATED);
        when(quizService.createQuiz(category, numQ, title)).thenReturn(expectedResponseEntity);

        // Act
        ResponseEntity<String> responseEntity = quizController.createQuiz(category, numQ, title);

        // Assert
        assertEquals(expectedResponseEntity, responseEntity);
        verify(quizService, times(1)).createQuiz(category, numQ, title);
    }

    @Test
    void testGetQuizQuestions() {
        // Arrange
        Integer id = 1;
        ResponseEntity<List<QuestionWrapper>> expectedResponseEntity = mock(ResponseEntity.class);
        when(quizService.getQuizQuestions(id)).thenReturn(expectedResponseEntity);

        // Act
        ResponseEntity<List<QuestionWrapper>> responseEntity = quizController.getQuizQuestions(id);

        // Assert
        assertEquals(expectedResponseEntity, responseEntity);
        verify(quizService, times(1)).getQuizQuestions(id);
    }

    @Test
    void testSubmitQuiz() {
        // Arrange
        Integer id = 1;
        List<Response> responses = mock(List.class);
        ResponseEntity<Integer> expectedResponseEntity = mock(ResponseEntity.class);
        when(quizService.calculateResult(id, responses)).thenReturn(expectedResponseEntity);

        // Act
        ResponseEntity<Integer> responseEntity = quizController.submitQuiz(id, responses);

        // Assert
        assertEquals(expectedResponseEntity, responseEntity);
        verify(quizService, times(1)).calculateResult(id, responses);
    }
}
