package com.passakorn.authentication.handler;


import com.passakorn.authentication.dto.ErrorResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorResponseHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseMessage> handleNotFoundException(Exception ex) {
        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseMessage> handleAuthenticationException(Exception ex) {
        return ResponseEntity
                .internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponseMessage(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }
}
