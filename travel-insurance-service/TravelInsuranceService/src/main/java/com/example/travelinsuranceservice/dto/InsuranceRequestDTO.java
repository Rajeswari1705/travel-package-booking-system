package com.example.travelinsuranceservice.dto;
 
import lombok.Data;
 
/**
* DTO to expose insurance coverage plans to Booking module.
*/
@Data
public class InsuranceRequestDTO {
   
    private Long insuranceId;
 
    private Long userId;
 
    private Long bookingId;  // ✅ To be updated after successful payment
 
    private String coverageDetails;
    
 
    private String coverageType;
 
    private double price;
 
    private String provider;
 
    private String status;  // e.g., "PENDING", "ISSUED"
}
 
 
 