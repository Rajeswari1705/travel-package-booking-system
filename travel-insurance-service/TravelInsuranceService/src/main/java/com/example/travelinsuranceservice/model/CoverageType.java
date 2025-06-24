package com.example.travelinsuranceservice.model;
 
import lombok.Getter;
 
/**
 * Enum to represent predefined coverage types.
 * Each type has fixed coverage details, price, and claimable amount.
 * Lombok @Getter is used to generate getter methods for each field.
 */
@Getter
public enum CoverageType {
 
    BASIC("Covers emergency medical expenses and trip cancellation for domestic travel.", 500.0, 100000.0),
    STANDARD("Includes medical coverage, trip cancellation, lost baggage, and flight delay protection.", 1000.0, 200000.0),
    PREMIUM("Comprehensive coverage including international medical support, evacuation, trip interruption, and personal liability", 2000.0, 500000.0);
 
    private final String coverageDetails;
    private final Double price;
    private final Double claimableAmount;
 
    /**
     * Constructor to initialize enum values.
     *
     * @param coverageDetails description of coverage
     * @param price price of the coverage
     * @param claimableAmount amount claimable under this plan
     */
    CoverageType(String coverageDetails, Double price, Double claimableAmount) {
        this.coverageDetails = coverageDetails;
        this.price = price;
        this.claimableAmount = claimableAmount;
    }
}
 