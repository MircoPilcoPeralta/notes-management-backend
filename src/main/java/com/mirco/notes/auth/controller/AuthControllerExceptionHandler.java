package com.mirco.notes.auth.controller;

import com.mirco.notes.auth.model.exceptions.RepeatedEmailException;
import com.mirco.notes.common.model.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerExceptionHandler {

    @ExceptionHandler(RepeatedEmailException.class)
    public ResponseEntity<StandardResponse> handleLabelNotFoundException(RepeatedEmailException ex) {
        StandardResponse response = StandardResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
