package com.booking.dto;

import lombok.Data;
 
/**
 * DTO to carry insurance-related data between the Booking module and the Insurance module.
 */
@Data
public class InsuranceRequestDTO {
   
    private Integer insuranceId;

    private Long userId;
 
    private Long bookingId;  // To be updated after successful payment
 
    private String coverageDetails;
 
    private String coverageType;
 
    private double price;
 
    private String provider;
 
    private String status;  // e.g., "PENDING", "ACTIVE", "CANCELLED"
}
 
