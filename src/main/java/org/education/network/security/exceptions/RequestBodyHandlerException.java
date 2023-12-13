package org.education.network.security.exceptions;

public class RequestBodyHandlerException extends RuntimeException{

    public RequestBodyHandlerException(String message) {
        super(message);
    }

    public RequestBodyHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestBodyHandlerException(Throwable cause) {
        super(cause);
    }

}
