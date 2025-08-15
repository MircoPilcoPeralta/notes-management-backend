package com.mirco.notes.label.model.exceptions;

public class LabelNotOwnedBySystemUserException extends RuntimeException {

    public LabelNotOwnedBySystemUserException(String message) {
        super(message);
    }

    public LabelNotOwnedBySystemUserException(String message, Throwable cause) {
        super(message, cause);
    }

}
