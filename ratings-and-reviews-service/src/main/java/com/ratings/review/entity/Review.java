package com.ratings.review.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.AllArgsConstructor; // Add this import
import lombok.NoArgsConstructor;  // Add this import, often good for JPA

/**
 * Entity representing a review for a travel package.
 */
@Data
@AllArgsConstructor // Generates constructor with all fields
@NoArgsConstructor  // Generates a no-argument constructor
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private Long userId;
    @Column(name = "package_id", nullable = false)
    private Long packageId;



    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    
}
