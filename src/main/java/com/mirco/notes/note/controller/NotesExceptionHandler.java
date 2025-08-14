package com.mirco.notes.note.controller;

import com.mirco.notes.label.model.exceptions.LabelNotFoundException;
import com.mirco.notes.note.model.exceptions.NoteNotFoundException;
import com.mirco.notes.note.model.exceptions.NoteWithLabelsWithNullIdException;
import com.mirco.notes.shared.model.exceptions.UserNotRegisteredException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.mirco.notes.shared.model.response.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

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

    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<StandardResponse> handleNoteNotFoundException(NoteNotFoundException ex) {
        StandardResponse response = StandardResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(NoteWithLabelsWithNullIdException.class)
    public ResponseEntity<StandardResponse> handleNoteWithLabelsWithNullIdException(NoteWithLabelsWithNullIdException ex) {
        StandardResponse response = StandardResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardResponse> handleNoteNotFoundException(MethodArgumentTypeMismatchException ex) {
        StandardResponse response = StandardResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Value of: '" + ex.getName() + "' have be of type: '"+ ex.getRequiredType().getSimpleName()+"'")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
