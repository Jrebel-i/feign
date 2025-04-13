package com.hucho.feign.demos.exception;

public class CollectException extends RuntimeException{

    private int status;
    private String message;

    public CollectException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
