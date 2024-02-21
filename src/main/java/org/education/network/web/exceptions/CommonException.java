package org.education.network.web.exceptions;

public class CommonException extends RuntimeException{

    public CommonException(String msg) {
        super(msg);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

}
