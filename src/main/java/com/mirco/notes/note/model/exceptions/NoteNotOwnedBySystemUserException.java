package com.mirco.notes.note.model.exceptions;

public class NoteNotOwnedBySystemUserException extends RuntimeException {

    public NoteNotOwnedBySystemUserException(String message) {
        super(message);
    }

    public NoteNotOwnedBySystemUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
