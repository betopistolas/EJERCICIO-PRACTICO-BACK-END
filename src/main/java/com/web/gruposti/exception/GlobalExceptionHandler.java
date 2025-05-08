package com.web.gruposti.exception;

import com.web.gruposti.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MarvelApiException.class)
    public ResponseEntity<ErrorResponse> handleMarvelApiError(MarvelApiException ex) {
        ErrorResponse error = new ErrorResponse(ex.getCode().toString(),ex.getMessage());
        error.setType("ERROR");
        error.setAction("CANCEL");
        error.setCode(ex.getCode());
        error.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}