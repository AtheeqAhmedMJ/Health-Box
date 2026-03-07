package com.healthbox.hmsbackend.common.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}