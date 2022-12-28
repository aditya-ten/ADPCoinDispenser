package com.adp.coindispenser.exceptions;

public class OutOfCoinsException extends RuntimeException {
    public OutOfCoinsException(String message) {
        super(message);
    }
}
