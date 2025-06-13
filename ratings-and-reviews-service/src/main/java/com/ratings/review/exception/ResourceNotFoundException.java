package com.ratings.review.exception;

/**
 * Custom exception for handling "Resource Not Found" errors.
 * Used when an entity such as Review, TravelPackage, or TravelAgent does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
