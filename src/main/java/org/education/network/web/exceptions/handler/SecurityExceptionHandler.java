package org.education.network.web.exceptions.handler;

import org.education.network.dto.response.CommonResponse;
import org.education.network.dto.response.ErrorRes;
import org.education.network.web.exceptions.AuthenticationAndAuthorizationNetworkException;
import org.education.network.web.exceptions.BadMinioRequestException;
import org.education.network.web.exceptions.FileHandlerException;
import org.education.network.web.exceptions.JwtException;
import org.education.network.web.exceptions.RequestBodyHandlerException;
import org.education.network.web.exceptions.SameUserException;
import org.education.network.web.exceptions.WrongJsonException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler {
    //todo отсортировать

    @ExceptionHandler(value = {
            SameUserException.class
    })
    protected ResponseEntity<Object> samePerson() {
        ErrorRes errorResponse = new ErrorRes(
                "Bad Request",
                "You tried to do an action with yourself");

        return ResponseEntity.status(404).body(CommonResponse.builder()
                .hasErrors(true)
                .body(errorResponse)
                .build());
    }

    @ExceptionHandler(value
            = { WrongJsonException.class })
    protected ResponseEntity<Object> wrongJson() {
        ErrorRes errorResponse = new ErrorRes(
                "Bad Request",
                "Wrong Json");

        return ResponseEntity.status(404).body(CommonResponse.builder()
                .hasErrors(true)
                .body(errorResponse)
                .build());
    }

    //todo исправить errorRepsonse message
    @ExceptionHandler(value
            = { Exception.class })
    protected ResponseEntity<Object> commonException(Exception e) {
        ErrorRes errorResponse = new ErrorRes(
                "Not Found",
                "Common error");

        return ResponseEntity.status(404).body(CommonResponse.builder()
                .hasErrors(true)
                .body(errorResponse)
                .build());
    }

    @ExceptionHandler(value
            = { SQLGrammarException.class })
    protected ResponseEntity<Object> sqlException(SQLGrammarException e) {
        ErrorRes errorResponse = new ErrorRes(
                "Not Found",
                e.getMessage());

        return ResponseEntity.status(404).body(CommonResponse.builder()
                .hasErrors(true)
                .body(errorResponse)
                .build());
    }

    @ExceptionHandler(SecurityException.class)
    protected ResponseEntity<Object> securityConfigError() {
        ErrorRes errorResponse = new ErrorRes(
                "BAD_REQUEST",
                "security config error");

        return ResponseEntity.status(400).body(CommonResponse.builder()
                .hasErrors(true)
                .body(errorResponse)
                .build());
    }

    @ExceptionHandler(BadMinioRequestException.class)
    protected ResponseEntity<Object> minioException(Exception ex) {
        return ResponseEntity.status(400).body(CommonResponse.builder()
                .hasErrors(true)
                .body(ex)
                .build());
    }

    @ExceptionHandler(FileHandlerException.class)
    protected ResponseEntity<Object> fileException(Exception ex) {
        return ResponseEntity.status(500).body(CommonResponse.builder()
                .hasErrors(true)
                .body(ex)
                .build());
    }

    @ExceptionHandler(AuthenticationAndAuthorizationNetworkException.class)
    protected ResponseEntity<Object> authenticationAndAuthorizationNetworkException(AuthenticationAndAuthorizationNetworkException e) {
        ErrorRes errorResponse = new ErrorRes(
                "Unauthorized",
                e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.valueOf("application/json")).body(
                CommonResponse.builder()
                .hasErrors(true)
                .body(errorResponse)
                .build()
        );

    }

    @ExceptionHandler(RequestBodyHandlerException.class)
    protected ResponseEntity<Object> requestBodyHandlerException(Exception ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                CommonResponse.builder()
                        .hasErrors(true)
                        .body(ex)
                        .build()
        );

    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<Object> jwtException(Exception ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                CommonResponse.builder()
                        .hasErrors(true)
                        .body(ex)
                        .build()
        );

    }

    //todo AuthenticationNetworkException Такой номер уже есть
}
