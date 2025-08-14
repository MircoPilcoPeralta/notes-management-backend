package com.mirco.notes.note.model.exceptions;

public class NoteWithLabelsWithNullIdException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Note with labels cannot have a null id.";

    public NoteWithLabelsWithNullIdException() {
        super(DEFAULT_MESSAGE);
    }

    public NoteWithLabelsWithNullIdException(String message) {
        super(message);
    }

    public NoteWithLabelsWithNullIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoteWithLabelsWithNullIdException(Throwable cause) {
        super(cause);
    }



}
