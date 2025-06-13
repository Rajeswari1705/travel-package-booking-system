package com.example.travelinsuranceservice.model;
 
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * Entity class mapped to 'insurance' table.
 * Uses Lombok to generate getters/setters and constructors.
 * Uses @PrePersist to auto-fill fields based on selected CoverageType.
 */
@Entity
@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-args constructor
public class Insurance {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer insuranceId; // Auto-generated primary key
 
    @NotNull(message = "User ID cannot be null")
    private Integer userId; // Received from external User module
 
    @NotNull(message = "Booking ID cannot be null")
    private Integer bookingId; // Received from Booking module
 
    private String coverageDetails; // Auto-filled from enum
 
    private String provider = "Secure Travel Insurance Co."; // Fixed insurance provider
 
    private String status = "Active"; // Default status
 
    private Double price; // Auto-filled based on coverage type
 
    private Double claimableAmount; // Auto-filled from coverage type
 
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Coverage Type is required")
    private CoverageType coverageType; // User selects during booking
 
    /**
     * Sets values for price, claimable amount, and details from selected coverage type.
     * This method is automatically called before the entity is inserted.
     */
    @PrePersist
    public void setValuesFromCoverageType() {
        if (coverageType != null) {
            this.coverageDetails = coverageType.getCoverageDetails();
            this.price = coverageType.getPrice();
            this.claimableAmount = coverageType.getClaimableAmount();
        }
    }
}
 