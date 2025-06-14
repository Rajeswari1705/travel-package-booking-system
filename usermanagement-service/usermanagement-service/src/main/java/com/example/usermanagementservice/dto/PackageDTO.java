package com.example.usermanagementservice.dto;

import java.time.LocalDate;
import java.util.List;
 
// This class represents the structure of package returned by travelpackage mangement service
public class PackageDTO {
    private Long packageId;  // Matches 'packageId' in TravelPackage
    private Long agentId;    // Maps to 'agentId'
    private String title;    // Matches 'title'
    private String description; // Maps to 'description'
    private int duration;    // Maps to 'duration'
    private double price;    // Maps to 'price'
    private int maxCapacity; // Matches 'maxCapacity'
    private LocalDate tripStartDate; // Matches 'tripStartDate'
    private LocalDate tripEndDate; // Maps to 'tripEndDate'
    private List<String> highlights; // Maps to 'highlights'

    // Getters and setters
    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public LocalDate getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(LocalDate tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public LocalDate getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(LocalDate tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public List<String> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<String> highlights) {
        this.highlights = highlights;
    }
}