package com.bebs.source.common.excetpion;

public class BizException extends RuntimeException {
    public BizException(String message) {
        super(message);
    }
    public BizException(String message, Throwable e) {
        super(message, e);
    }
}


