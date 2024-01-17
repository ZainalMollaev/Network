package org.education.network.security.exceptions;

public class FileHandlerException extends RuntimeException {

    public FileHandlerException(String message) {
        super(message);
    }

    public FileHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileHandlerException(Throwable cause) {
        super(cause);
    }

}
