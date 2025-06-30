// src/main/java/com/ratings/review/dto/TravelPackageDTO.java
package com.ratings.review.dto;
import lombok.Data;
import lombok.AllArgsConstructor; // Add this import
import lombok.NoArgsConstructor;  // Add this import, often good for JPA
@Data
@AllArgsConstructor // Generates constructor with all fields
@NoArgsConstructor  // Generates a no-argument constructor
public class TravelPackageDTO {
    private Long packageId;
    private Long agentId;
    private String title;
    private String imageUrl; 
    private String description; 
    private double price;       
    private int duration;      
    // ... add any other fields your frontend's package-details.component.html uses
}