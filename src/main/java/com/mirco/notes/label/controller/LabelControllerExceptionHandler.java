package com.mirco.notes.label.controller;

import com.mirco.notes.label.model.exceptions.LabelNotFoundException;
import com.mirco.notes.shared.model.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LabelControllerExceptionHandler {
    @ExceptionHandler(LabelNotFoundException.class)
    public ResponseEntity<StandardResponse> handleLabelNotFoundException(LabelNotFoundException ex) {
        StandardResponse response = StandardResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
