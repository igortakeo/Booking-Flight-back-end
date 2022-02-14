package com.bookingflight.bookingflight.domain.services.exceptions;

public class ObjectNotAllowedToBeDeletedException extends RuntimeException{

    public ObjectNotAllowedToBeDeletedException(String message) {
        super(message);
    }
}