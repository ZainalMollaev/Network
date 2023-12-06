package org.education.network.security.exceptions.handler;

import org.education.network.security.exceptions.AuthenticationNetworkException;
import org.education.network.security.model.response.CommonResponse;
import org.education.network.security.model.response.ErrorRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(value
            = { BadCredentialsException.class, InternalAuthenticationServiceException.class })
    protected ResponseEntity<Object> handleLogin() {

        ErrorRes errorResponse = new ErrorRes(
                "BAD_REQUEST",
                "Invalid username or password");

        return ResponseEntity.status(400).body(CommonResponse.builder()
                .hasErrors(true)
                .body(errorResponse)
                .build()
                .toString());
    }

    @ExceptionHandler(AuthenticationNetworkException.class)
    protected ResponseEntity<Object> authenticationException() {

        ErrorRes errorResponse = new ErrorRes(
                "UNAUTHORIZED",
                "Unauthorized");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.valueOf("application/json")).body(
                CommonResponse.builder()
                .hasErrors(true)
                .body(errorResponse)
                .build()
                .toString()
        );

    }

}
