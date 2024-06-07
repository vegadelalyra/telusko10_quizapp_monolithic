package com.vegadelalyra.quizapp.quiz.model;

import com.vegadelalyra.quizapp.question.model.Question;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String title;
    @ManyToMany
    private List<Question> questions;
}
