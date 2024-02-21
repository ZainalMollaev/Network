package org.education.network.web.exceptions;

public class SameUserException extends RuntimeException {

    public SameUserException(String message) {
        super(message);
    }

    public SameUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameUserException(Throwable cause) {
        super(cause);
    }
}
