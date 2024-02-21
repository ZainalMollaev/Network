package org.education.network.web.exceptions;

public class WrongJsonException extends RuntimeException{

    public WrongJsonException(String message) {
        super(message);
    }

    public WrongJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongJsonException(Throwable cause) {
        super(cause);
    }

}
