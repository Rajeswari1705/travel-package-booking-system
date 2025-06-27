// src/main/java/com/ratings/review/dto/TravelPackageDTO.java
package com.ratings.review.dto;
import lombok.Data;

@Data
public class TravelPackageDTO {
    private Long packageId;
    private Long agentId;
    private String title;
    private String imageUrl; // Add this line
    private String description; // Add this line
    private double price;       // Add this line
    private int duration;       // Add this line
    // ... add any other fields your frontend's package-details.component.html uses
}