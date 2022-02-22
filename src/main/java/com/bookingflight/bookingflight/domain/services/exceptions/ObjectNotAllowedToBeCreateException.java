package com.bookingflight.bookingflight.domain.services.exceptions;

public class ObjectNotAllowedToBeCreateException extends RuntimeException{

    public ObjectNotAllowedToBeCreateException(String message) {
        super(message);
    }
}
