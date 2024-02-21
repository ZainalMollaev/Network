package org.education.network.web.exceptions;

public class AuthenticationAndAuthorizationNetworkException extends RuntimeException {

    public AuthenticationAndAuthorizationNetworkException(String message) {
        super(message);
    }

    public AuthenticationAndAuthorizationNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationAndAuthorizationNetworkException(Throwable cause) {
        super(cause);
    }
}
