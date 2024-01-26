package org.education.network.web.exceptions;

public class BadMinioRequestException extends RuntimeException{

    public BadMinioRequestException(String message) {
        super(message);
    }

    public BadMinioRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadMinioRequestException(Throwable cause) {
        super(cause);
    }
}
