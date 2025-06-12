package com.example.travelinsuranceservice.model;
 
import jakarta.persistence.*;
 
@Entity
public class Insurance {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer insuranceId;
 
    private Integer userId;
    private Integer bookingId;
    private String coverageDetails;
    private String provider;
    private String status;
    private Double price;
    private Double claimableAmount;
 
    // Getters and Setters
    public Integer getInsuranceId() {
        return insuranceId;
    }
 
    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }
 
    public Integer getUserId() {
        return userId;
    }
 
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
 
    public Integer getBookingId() {
        return bookingId;
    }
 
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }
 
    public String getCoverageDetails() {
        return coverageDetails;
    }
 
    public void setCoverageDetails(String coverageDetails) {
        this.coverageDetails = coverageDetails;
    }
 
    public String getProvider() {
        return provider;
    }
 
    public void setProvider(String provider) {
        this.provider = provider;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public Double getPrice() {
        return price;
    }
 
    public void setPrice(Double price) {
        this.price = price;
    }
 
    public Double getClaimableAmount() {
        return claimableAmount;
    }
 
    public void setClaimableAmount(Double claimableAmount) {
        this.claimableAmount = claimableAmount;
    }
}
 