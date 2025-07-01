package com.booking.exception;

/**
 * Custom exception used to indicate business rule violations or validation failures
 * that should result in a 400 Bad Request error.
 */
public class CustomBusinessException extends RuntimeException {

    public CustomBusinessException(String message) {
        super(message);
    }

    public CustomBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}