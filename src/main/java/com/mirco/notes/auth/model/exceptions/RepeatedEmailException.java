package com.mirco.notes.auth.model.exceptions;

public class RepeatedEmailException  extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Email is already in use.";

    public RepeatedEmailException() {
        super(DEFAULT_MESSAGE);
    }

    public RepeatedEmailException(String message) {
        super(message);
    }

    public RepeatedEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatedEmailException(Throwable cause) {
        super(cause);
    }
}
