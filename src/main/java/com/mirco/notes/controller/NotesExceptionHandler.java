package com.mirco.notes.controller;

import com.mirco.notes.notes.model.exceptions.NoteNotFoundException;
import com.mirco.shared.model.exceptions.UserNotRegisteredException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardResponse> handleNoteNotFoundException(MethodArgumentTypeMismatchException ex) {
        StandardResponse response = StandardResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<StandardResponse> handleNoteNotFoundException(NoteNotFoundException ex) {
        StandardResponse response = StandardResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
