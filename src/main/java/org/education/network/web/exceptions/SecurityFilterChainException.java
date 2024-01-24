package org.education.network.web.exceptions;

public class SecurityFilterChainException extends RuntimeException{

    public SecurityFilterChainException(String message) {
        super(message);
    }

    public SecurityFilterChainException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityFilterChainException(Throwable cause) {
        super(cause);
    }

}
