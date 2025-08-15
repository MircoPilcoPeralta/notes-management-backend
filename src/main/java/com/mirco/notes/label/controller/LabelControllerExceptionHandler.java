package com.mirco.notes.label.controller;

import com.mirco.notes.label.model.exceptions.LabelNotFoundException;
import com.mirco.notes.label.model.exceptions.LabelNotOwnedBySystemUserException;
import com.mirco.notes.common.model.response.StandardResponse;
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

    @ExceptionHandler(LabelNotOwnedBySystemUserException.class)
    public ResponseEntity<StandardResponse<Void>> handleLabelOwnershipException(LabelNotOwnedBySystemUserException ex) {
        StandardResponse<Void> response = StandardResponse.<Void>builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
