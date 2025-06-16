package com.example.travelinsuranceservice.model;
 
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * Represents an insurance record for a user's travel booking.
 * bookingId is set later after booking is created.
 */
@Entity
@Data
@NoArgsConstructor
public class Insurance {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer insuranceId;
 
    @NotNull(message = "User ID cannot be null")
    private Integer userId;
 
    // Optional field, set later
    private Integer bookingId;
 
    private String coverageDetails;
 
    private String provider = "Secure Travel Insurance Co.";
 
    private String status = "Active";
 
    private Double price;
 
    private Double claimableAmount;
 
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Coverage Type is required")
    private CoverageType coverageType;
 
    @PrePersist
    public void setValuesFromCoverageType() {
        if (coverageType != null) {
            this.coverageDetails = coverageType.getCoverageDetails();
            this.price = coverageType.getPrice();
            this.claimableAmount = coverageType.getClaimableAmount();
        }
    }
}
 