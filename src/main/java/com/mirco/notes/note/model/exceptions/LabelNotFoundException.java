package com.mirco.notes.note.model.exceptions;

public class LabelNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Label not found.";

    public LabelNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public LabelNotFoundException(String message) {
        super(message);
    }

    public LabelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
