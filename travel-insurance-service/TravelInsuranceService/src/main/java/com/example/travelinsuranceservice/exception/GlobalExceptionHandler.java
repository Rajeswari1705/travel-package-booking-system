package com.example.travelinsuranceservice.exception;
 
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
 
import java.util.*;
 
/**
 * Global exception handler to manage all exceptions in a uniform way.
 * Handles validation errors, resource not found, and general exceptions.
 */
@RestControllerAdvice // Tells Spring this class handles exceptions across the entire app
public class GlobalExceptionHandler {
 
    /**
     * Handles ResourceNotFoundException (custom).
     *
     * @param ex custom exception
     * @return map with error message and 404 NOT FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage()); // Message comes from exception thrown in service
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
 
    /**
     * Handles @Valid validation errors (like missing fields or null values).
     *
     * @param ex Spring’s built-in validation error object
     * @return map of all field errors and messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
            .forEach(e -> errors.put(e.getField(), e.getDefaultMessage())); // Collect all field validation errors
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
 
    /**
     * Handles any other unexpected or unhandled exception.
     *
     * @param ex the uncaught exception
     * @return generic message with 500 INTERNAL SERVER ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
 
