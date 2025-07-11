package com.example.travelinsuranceservice.model;
 
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
 
import java.time.LocalDateTime;
 
/**
 * Entity representing an insurance policy for travel booking.
 */
@Entity
@Data
public class Insurance {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer insuranceId;
 
    @NotNull(message = "User ID cannot be null")
    private Long userId;
 
    // Booking ID will be updated later from the Booking module
    @Column(name="booking_id")
    private Long bookingId;
 
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Coverage type is required")
    private CoverageType coverageType;
 
    // Fields populated based on coverageType at creation
    private String coverageDetails;
    private Double price;
    private Double claimableAmount;
 
    // Default Status Pending
    @Column(nullable=false)
    private String issuanceStatus = "PENDING";
    
    @Column(nullable=false)
    private String provider="Secure Travel Insurance Co.";
 
    @CreationTimestamp
    private LocalDateTime createdAt;
 
    /**
     * Automatically sets coverageDetails, price, and claimableAmount
     * before saving to DB based on the selected coverageType.
     */
    @PrePersist
    public void setDefaults() {
        if (coverageType != null) {
            this.coverageDetails = coverageType.getCoverageDetails();
            this.price = coverageType.getPrice();
            this.claimableAmount = coverageType.getClaimableAmount();
        }
    }
}
 