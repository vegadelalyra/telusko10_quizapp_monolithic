package com.vegadelalyra.quizapp.quiz.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class Response {
    private Integer id;
    private String response;

    public Response(Integer id, String response) {
        this.id = id;
        this.response = response;
    }
}
