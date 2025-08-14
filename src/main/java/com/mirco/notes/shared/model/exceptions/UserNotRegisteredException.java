package com.mirco.notes.shared.model.exceptions;

public class UserNotRegisteredException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "User is not registered.";

    public UserNotRegisteredException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotRegisteredException(String message) {
        super(message);
    }

    public UserNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotRegisteredException(Throwable cause) {
        super(cause);
    }
}
