package com.vegadelalyra.quizapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class HttpHandler {

    public static <body> ResponseEntity generateResponse(@Nullable body body, HttpStatus status) {
        try {
            return new ResponseEntity<>(body, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }
}
