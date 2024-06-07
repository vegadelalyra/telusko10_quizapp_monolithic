package com.vegadelalyra.quizapp.quiz.service;

import com.vegadelalyra.quizapp.question.dao.QuestionDAO;
import com.vegadelalyra.quizapp.question.model.Question;
import com.vegadelalyra.quizapp.question.model.QuestionWrapper;
import com.vegadelalyra.quizapp.quiz.dao.QuizDao;
import com.vegadelalyra.quizapp.quiz.model.Quiz;
import com.vegadelalyra.quizapp.quiz.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDAO questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> randomQuestions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(randomQuestions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        questionsFromDB.forEach(question -> {
            QuestionWrapper wrappedQuestion = new QuestionWrapper(
                    question.getId(), question.getTitle(), question.getOption1(),
                    question.getOption2(), question.getOption3(), question.getOption4());
            questionsForUser.add(wrappedQuestion);
        });

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        int score = (int) responses.stream()
                .filter(response -> questionDao.checkAnswer(response.getId(), response.getResponse()))
                .count();


//        Quiz quiz = quizDao.findById(id).get();
//        List<Question> questions = quiz.getQuestions();
//        int score = 0;
//        int i = 0;
//        for (Response response : responses) {
//            if (response.getResponse().equals(questions.get(i).getAnswer())) score++;
//            i++;
//        }


        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
