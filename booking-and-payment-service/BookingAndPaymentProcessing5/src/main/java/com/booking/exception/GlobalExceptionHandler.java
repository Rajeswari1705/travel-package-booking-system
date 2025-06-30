package com.booking.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global exception handler for the application.
 * 
 * This class handles various exceptions that may occur during the execution of the application
 * and provides appropriate responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle custom business exceptions.
     * 
     * @param ex The CustomBusinessException instance.
     * @return ResponseEntity containing the error details and HTTP status.
     */
    @ExceptionHandler(CustomBusinessException.class)
    public ResponseEntity<?> handleBusinessError(CustomBusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("success", false, "message", ex.getMessage()));
    }

    /**
     * Handle Feign client exceptions.
     * 
     * @param ex The FeignException instance.
     * @return ResponseEntity containing the error details and HTTP status.
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleFeignError(FeignException ex) {
        return ResponseEntity.status(ex.status())
                .body(Map.of("success", false, "message", "Feign call failed", "details", ex.getMessage()));
    }

    /**
     * Handle general exceptions.
     * 
     * @param ex The Exception instance.
     * @return ResponseEntity containing the error details and HTTP status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralError(Exception ex) {
        log.error("Unhandled error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "message", "Unexpected error occurred", "details", ex.getMessage()));
    }
}
