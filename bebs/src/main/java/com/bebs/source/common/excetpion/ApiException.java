package com.bebs.source.common.excetpion;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
    public ApiException(String message, Throwable e) {
        super(message, e);
    }
}


