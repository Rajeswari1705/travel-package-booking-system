package com.example.travelinsuranceservice.dto;
 
import lombok.AllArgsConstructor;
import lombok.Data;
 
/**
 * DTO to expose insurance coverage plans to Booking module.
 */
@Data
@AllArgsConstructor
public class CoveragePlanDTO {
    private String coverageType;       // BASIC, STANDARD, PREMIUM
    private String coverageDetails;    // Human-readable description
    private Double price;              // Fixed price per plan
    private Double claimableAmount;    // Max claim amount per plan
}
 
