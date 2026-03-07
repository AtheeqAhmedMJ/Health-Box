package com.healthbox.hmsbackend.common.exception;

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message);
    }
}