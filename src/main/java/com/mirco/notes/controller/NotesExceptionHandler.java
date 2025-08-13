package com.mirco.notes.controller;

import com.mirco.shared.model.exceptions.UserNotRegisteredException;
import com.mirco.shared.model.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotesExceptionHandler {

    @ExceptionHandler(UserNotRegisteredException.class)
    public ResponseEntity<StandardResponse> handleUserNotRegisteredException(UserNotRegisteredException ex) {
            StandardResponse response = StandardResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("User not registered")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
