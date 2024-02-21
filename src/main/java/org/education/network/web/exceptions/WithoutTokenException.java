package org.education.network.web.exceptions;

public class WithoutTokenException extends RuntimeException{

    public WithoutTokenException(String message) {
        super(message);
    }

    public WithoutTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public WithoutTokenException(Throwable cause) {
        super(cause);
    }

}
