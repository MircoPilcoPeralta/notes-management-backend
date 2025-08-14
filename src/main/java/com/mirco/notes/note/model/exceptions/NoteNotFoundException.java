package com.mirco.notes.note.model.exceptions;

public class NoteNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Note not found";

    public NoteNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteNotFoundException(Throwable cause) {
        super(cause);
    }


}
