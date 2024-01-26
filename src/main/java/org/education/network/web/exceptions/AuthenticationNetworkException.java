package org.education.network.web.exceptions;

public class AuthenticationNetworkException extends RuntimeException {

    public AuthenticationNetworkException(String message) {
        super(message);
    }

    public AuthenticationNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationNetworkException(Throwable cause) {
        super(cause);
    }
}
