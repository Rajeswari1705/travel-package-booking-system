package com.example.travelinsuranceservice.exception;
 
/**
 * Custom exception to be thrown when an entity is not found.
 * Used for insuranceId and requestId not present in DB.
 */
public class ResourceNotFoundException extends RuntimeException {
 
    // Added to avoid serialization warning
    private static final long serialVersionUID = 1L;
 
    /**
     * Constructor that passes the error message to parent RuntimeException.
     *
     * @param message details of the exception
     */
    public ResourceNotFoundException(String message) {
        super(message); // Calls parent class constructor
    }
}
 