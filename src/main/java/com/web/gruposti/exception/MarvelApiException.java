package com.web.gruposti.exception;

public class MarvelApiException extends RuntimeException {

    private String code;

    public MarvelApiException(String code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
