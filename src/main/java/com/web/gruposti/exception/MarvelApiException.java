package com.web.gruposti.exception;

public class MarvelApiException extends RuntimeException {
    public MarvelApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
