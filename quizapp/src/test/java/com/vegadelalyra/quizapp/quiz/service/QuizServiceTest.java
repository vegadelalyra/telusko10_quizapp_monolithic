package com.vegadelalyra.quizapp.quiz.service;

import com.vegadelalyra.quizapp.question.dao.QuestionDAO;
import com.vegadelalyra.quizapp.question.model.Question;
import com.vegadelalyra.quizapp.question.model.QuestionWrapper;
import com.vegadelalyra.quizapp.quiz.dao.QuizDao;
import com.vegadelalyra.quizapp.quiz.model.Quiz;
import com.vegadelalyra.quizapp.quiz.model.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizDao quizDao;

    @Mock
    private QuestionDAO questionDao;

    @InjectMocks
    private QuizService quizService;

    @Test
    void testCreateQuiz() {
        // Arrange
        String category = "Category";
        int numQ = 5;
        String title = "Quiz Title";
        List<Question> randomQuestions = new ArrayList<>();
        when(questionDao.findRandomQuestionsByCategory(category, numQ)).thenReturn(randomQuestions);
        Quiz quiz = new Quiz();
        when(quizDao.save(any(Quiz.class))).thenReturn(quiz);

        // Act
        ResponseEntity<String> responseEntity = quizService.createQuiz(category, numQ, title);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody());
        verify(questionDao, times(1)).findRandomQuestionsByCategory(category, numQ);
        verify(quizDao, times(1)).save(any(Quiz.class));
    }

    @Test
    void testGetQuizQuestions() {
        // Arrange
        Integer id = 1;
        Quiz quiz = new Quiz();
        List<Question> questions = new ArrayList<>();
        quiz.setQuestions(questions);
        when(quizDao.findById(id)).thenReturn(Optional.of(quiz));

        // Act
        ResponseEntity<List<QuestionWrapper>> responseEntity = quizService.getQuizQuestions(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(questions.size(), responseEntity.getBody().size());
    }

    @Test
    void testCalculateResult() {
        // Arrange
        Integer id = 1;
        List<Response> responses = new ArrayList<>();
        responses.add(new Response(1, "Correct"));
        responses.add(new Response(2, "Incorrect"));
        when(questionDao.checkAnswer(eq(1), eq("Correct"))).thenReturn(true);
        when(questionDao.checkAnswer(eq(2), eq("Incorrect"))).thenReturn(false);

        // Act
        ResponseEntity<Integer> responseEntity = quizService.calculateResult(id, responses);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody());
        verify(questionDao, times(1)).checkAnswer(eq(1), eq("Correct"));
        verify(questionDao, times(1)).checkAnswer(eq(2), eq("Incorrect"));
    }

    @Test
    void testGetQuizQuestionsWithQuestions() {
        // Arrange
        Integer id = 1;
        Quiz quiz = new Quiz();
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setId(1);
        question1.setTitle("Question 1");
        question1.setOption1("Option 1");
        question1.setOption2("Option 2");
        question1.setOption3("Option 3");
        question1.setOption4("Option 4");
        questions.add(question1);
        quiz.setQuestions(questions);
        when(quizDao.findById(id)).thenReturn(Optional.of(quiz));

        // Act
        ResponseEntity<List<QuestionWrapper>> responseEntity = quizService.getQuizQuestions(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<QuestionWrapper> questionWrappers = responseEntity.getBody();
        assertEquals(questions.size(), questionWrappers.size());
        QuestionWrapper questionWrapper = questionWrappers.get(0);
        assertEquals(question1.getId(), questionWrapper.getId());
        assertEquals(question1.getTitle(), questionWrapper.getTitle());
        assertEquals(question1.getOption1(), questionWrapper.getOption1());
        assertEquals(question1.getOption2(), questionWrapper.getOption2());
        assertEquals(question1.getOption3(), questionWrapper.getOption3());
        assertEquals(question1.getOption4(), questionWrapper.getOption4());
    }

}
