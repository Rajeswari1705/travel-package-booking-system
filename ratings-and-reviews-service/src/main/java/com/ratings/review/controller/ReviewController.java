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
 * This class provides REST endpoints for customers to post, retrieve, update,
 * and delete their reviews, as well as fetch average ratings.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    /**
     * Posts a new review for a travel package.
     * The timestamp for the review is automatically set to the current time.
     *
     * @param review The Review object containing details like userId, packageId, comment, and rating.
     * @return ResponseEntity containing a map with details of the newly saved review,
     * including its generated reviewId.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> postReview(@RequestBody Review review) {
        logger.info("Posting a new review.");
        
        review.setTimestamp(LocalDateTime.now());
        Review savedReview = reviewService.postReview(review); // Save review through service layer
        logger.info("Review posted successfully with ID");

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
     * Retrieves all reviews associated with the specified travel package.
     *
     * @param packageId The ID of the travel package to retrieve reviews for.
     * @return A list of Review objects for the given package ID.
     */
    @GetMapping("/{packageId}")
    public List<Review> getReviewsByPackage(@PathVariable Long packageId) {
        logger.info("Fetching reviews for package ID {}", packageId);
        return reviewService.getReviewsByPackage(packageId);
    }
    
    /**
     * Calculates and retrieves the average rating for a specific travel package.
     *
     * @param packageId The ID of the travel package to calculate the average rating for.
     * @return ResponseEntity containing the average rating as a Double.
     */
    @GetMapping("/{packageId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long packageId) {
    	 logger.info("Calculating average rating for package ID {}", packageId);
    	double avgRating = reviewService.getAverageRatingForPackage(packageId);
    	logger.info("Average rating for package ID {} is: {}", packageId, avgRating);
    	return ResponseEntity.ok(avgRating);
    }

    /**
     * Checks if a specific user has already reviewed a given travel package.
     * This ensures a customer can only submit one review per package.
     *
     * @param userId The ID of the user.
     * @param packageId The ID of the travel package.
     * @return true if the user has already reviewed the package, false otherwise.
     */
    @GetMapping("/exists/{userId}/{packageId}")
    public boolean hasReviewed(@PathVariable Long userId, @PathVariable Long packageId) {
        logger.info("Checking if userId: {} has already reviewed packageId: {}", userId, packageId);
        boolean hasReviewed = reviewService.hasAlreadyReviewed(userId, packageId);
        logger.info("User {} has {} reviewed package {}.", userId, hasReviewed ? "already" : "not yet", packageId);
        return hasReviewed;
    }
    /**
     * Updates an existing review identified by its ID.
     *
     * @param reviewId The ID of the review to be updated.
     * @param updatedReview The Review object containing the updated fields (e.g., comment, rating).
     * @return ResponseEntity containing the updated Review object.
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId, @RequestBody Review updatedReview) {
        logger.info("Received request to update Review ID {}", reviewId);
        Review resultReview = reviewService.updateReview(reviewId, updatedReview);
        logger.info("Review with ID {} updated successfully.", reviewId);
        return ResponseEntity.ok(resultReview);
    }


    /**
     * Deletes a review by its ID.
     *
     * @param reviewId The ID of the review to be deleted.
     * @return ResponseEntity confirming the deletion.
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        logger.info("Deleting Review ID {}", reviewId);
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully.");
    }
}
