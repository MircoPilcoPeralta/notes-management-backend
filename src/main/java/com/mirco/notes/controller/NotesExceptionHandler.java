package com.mirco.notes.controller;

import com.mirco.notes.model.exceptions.LabelNotFoundException;
import com.mirco.notes.model.exceptions.NoteNotFoundException;
import com.mirco.notes.model.exceptions.NoteWithLabelsWithNullIdException;
import com.mirco.shared.model.exceptions.UserNotRegisteredException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.mirco.shared.model.response.StandardResponse;
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

    @ExceptionHandler(LabelNotFoundException.class)
    public ResponseEntity<StandardResponse> handleLabelNotFoundException(LabelNotFoundException ex) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponse> handleValidationException(MethodArgumentNotValidException exception) {
        HashMap<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        StandardResponse standardResponse = StandardResponse
                .builder()
                .message("Validation failed")
                .data(errors)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardResponse);
    }

}
