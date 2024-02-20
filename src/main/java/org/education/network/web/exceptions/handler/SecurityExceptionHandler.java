package org.education.network.web.exceptions.handler;

import org.education.network.dto.response.CommonResponse;
import org.education.network.dto.response.ErrorRes;
import org.education.network.web.exceptions.AuthenticationNetworkException;
import org.education.network.web.exceptions.BadMinioRequestException;
import org.education.network.web.exceptions.EmailExistException;
import org.education.network.web.exceptions.FileHandlerException;
import org.education.network.web.exceptions.JwtException;
import org.education.network.web.exceptions.RequestBodyHandlerException;
import org.education.network.web.exceptions.WrongJsonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler {
    //todo Сделать ошибку при запросе без токена

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

    @ExceptionHandler(value
            = { EmailExistException.class })
    protected ResponseEntity<Object> existingEmail() {
        ErrorRes errorResponse = new ErrorRes(
                "Bad Request",
                "Email is already exist!");

        return ResponseEntity.status(404).body(CommonResponse.builder()
                .hasErrors(true)
                .body(errorResponse)
                .build());
    }

    @ExceptionHandler(value
            = { Exception.class })
    protected ResponseEntity<Object> commonException() {
        ErrorRes errorResponse = new ErrorRes(
                "Not Found",
                "Common error");

        return ResponseEntity.status(404).body(CommonResponse.builder()
                .hasErrors(true)
                .body(errorResponse)
                .build());
    }

    @ExceptionHandler(value
            = { BadCredentialsException.class, InternalAuthenticationServiceException.class })
    protected ResponseEntity<Object> handleLogin() {
        ErrorRes errorResponse = new ErrorRes(
                "BAD_REQUEST",
                "Invalid username or password");

        return ResponseEntity.status(400).body(CommonResponse.builder()
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

    //todo Добавить InternalAuthenticationServiceException при неправильном логине или пароле
    //todo AuthenticationNetworkException Такой номер уже есть
    //todo Вынести в общий класс
}
