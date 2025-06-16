package com.example.travelinsuranceservice.exception;
 
/**
 * Custom exception used when user or booking ID is invalid.
 * Thrown when validation via Feign client fails.
 */
public class InvalidInputException extends RuntimeException {
 
    private static final long serialVersionUID = 1L;
 
    public InvalidInputException(String message) {
        super(message);
    }
}
 
