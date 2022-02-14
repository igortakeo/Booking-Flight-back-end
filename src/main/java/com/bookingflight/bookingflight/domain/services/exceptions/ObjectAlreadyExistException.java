package com.bookingflight.bookingflight.domain.services.exceptions;

public class ObjectAlreadyExistException extends RuntimeException{

    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}