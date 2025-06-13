package com.travel.review.controller;

import com.travel.review.entity.Review;
import com.travel.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @PostMapping // Customers can add reviews
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        return ResponseEntity.ok(service.addReview(review));
    }

    @GetMapping("/package/{packageId}") // Publicly accessible
    public List<Review> getByPackage(@PathVariable Long packageId) {
        return service.getReviewsByPackageId(packageId);
    }

    @GetMapping("/user/{userId}") // Publicly accessible (or could be restricted for user's own reviews)
    public List<Review> getByUser(@PathVariable Long userId) {
        return service.getReviewsByUserId(userId);
    }

    @PutMapping("/{id}") // Customers can update their own reviews (logic in service)
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Review updated = service.updateReview(id, review);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}") // Customers can delete their own reviews (logic in service)
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        service.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    // New endpoint for travel agent response
    @PostMapping("/{reviewId}/respond") // Travel agents can respond to reviews
    public ResponseEntity<Review> addAgentResponse(@PathVariable Long reviewId, @RequestBody String agentResponse) {
        // This method will now update the existing review with an agent response.
        // The service layer will handle finding the review and updating it.
        Review updatedReview = service.addAgentResponseToReview(reviewId, agentResponse);
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        }
        return ResponseEntity.notFound().build();
    }
}