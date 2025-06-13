package com.example.travelinsuranceservice.model;
 
/**
 * Enum to represent predefined coverage types.
 * Each type has fixed coverage details, price, and claimable amount.
 */
public enum CoverageType {
    BASIC("Basic Coverage", 2000.0, 100000.0),
    STANDARD("Standard Coverage", 4000.0, 200000.0),
    PREMIUM("Premium Coverage", 6000.0, 500000.0);
 
    private final String coverageDetails;
    private final Double price;
    private final Double claimableAmount;
 
    // Constructor to initialize the enum constants with values
    CoverageType(String coverageDetails, Double price, Double claimableAmount) {
        this.coverageDetails = coverageDetails;
        this.price = price;
        this.claimableAmount = claimableAmount;
    }
 
    // Getter methods for each field
    public String getCoverageDetails() {
        return coverageDetails;
    }
 
    public Double getPrice() {
        return price;
    }
 
    public Double getClaimableAmount() {
        return claimableAmount;
    }
}
 
