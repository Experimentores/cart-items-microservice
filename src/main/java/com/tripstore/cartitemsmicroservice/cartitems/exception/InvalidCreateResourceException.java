package com.tripstore.cartitemsmicroservice.cartitems.exception;

public class InvalidCreateResourceException extends RuntimeException {
    public InvalidCreateResourceException() {

    }
    public InvalidCreateResourceException(String message) {
        super(message);
    }
}
