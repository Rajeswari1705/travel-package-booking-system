package com.ratings.review.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime; // ✅ Used for timestamps  
import java.util.HashMap; // ✅ Required for creating response maps  
import java.util.Map; // ✅ Helps structure JSON response  

import com.ratings.review.entity.Review;
import com.ratings.review.exception.ResourceNotFoundException;
import com.ratings.review.repository.ReviewRepository;
import com.ratings.review.service.ReviewService;

import java.util.List;

/**
 * Controller for managing travel package reviews.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    /**
     * Post a new review
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> postReview(@RequestBody Review review) {
        logger.info("Posting a new review.");
        
        review.setTimestamp(LocalDateTime.now());
        Review savedReview = reviewService.postReview(review); // Save review through service layer

        //  Ensure reviewId is included in the response
        Map<String, Object> response = new HashMap<>();
        response.put("reviewId", savedReview.getReviewId()); // Add reviewId here
        response.put("userId", savedReview.getUserId());
        response.put("packageId", savedReview.getPackageId());
        response.put("comment", savedReview.getComment());
        response.put("rating", savedReview.getRating());
        response.put("timestamp", savedReview.getTimestamp());

        return ResponseEntity.ok(response);
    }





    /**
     * Retrieve all reviews associated with the specified travel package
     */
    @GetMapping("/{packageId}")
    public List<Review> getReviewsByPackage(@PathVariable Long packageId) {
        logger.info("Fetching reviews for package ID {}", packageId);
        return reviewService.getReviewsByPackage(packageId);
    }
    
    /** 
     * average rating
     */
    @GetMapping("/{packageId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long packageId) {
        double avgRating = reviewService.getAverageRatingForPackage(packageId);
        return ResponseEntity.ok(avgRating);
    }

//To verify customer given review or not-- only one time he can give review
    @GetMapping("/exists/{userId}/{packageId}")
    public boolean hasReviewed(@PathVariable Long userId, @PathVariable Long packageId) {
        return reviewService.hasAlreadyReviewed(userId, packageId);
    }
    /**
     * Update an existing review
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody Review updatedReview) {
        logger.info("Updating Review ID {}", reviewId);
        return ResponseEntity.ok(reviewService.updateReview(reviewId, updatedReview));
    }

    /**
     * Delete a review
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        logger.info("Deleting Review ID {}", reviewId);
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully.");
    }
}
