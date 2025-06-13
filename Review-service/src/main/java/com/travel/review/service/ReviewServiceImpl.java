package com.travel.review.service;

import com.travel.review.entity.Review;
import com.travel.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Override
    public Review addReview(Review review) {
        // Here, you might implement moderation logic before saving
        return repository.save(review);
    }

    @Override
    public List<Review> getReviewsByPackageId(Long packageId) {
        return repository.findByPackageId(packageId);
    }

    @Override
    public List<Review> getReviewsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Review updateReview(Long id, Review updatedReview) {
        Optional<Review> existing = repository.findById(id);
        if (existing.isPresent()) {
            Review r = existing.get();
            // Assuming only rating and comment can be updated by the customer
            r.setRating(updatedReview.getRating());
            r.setComment(updatedReview.getComment());
            // You might add logic here to ensure only the owner can update their review
            return repository.save(r);
        }
        return null; // Or throw an exception
    }

    @Override
    public void deleteReview(Long id) {
        // You might add logic here to ensure only the owner or an admin can delete a review
        repository.deleteById(id);
    }

    @Override
    public Review addAgentResponseToReview(Long reviewId, String agentResponse) {
        Optional<Review> existingReview = repository.findById(reviewId);
        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            review.setAgentResponse(agentResponse);
            review.setAgentResponseTimestamp(LocalDateTime.now());
            // Here, you might also want to set the agent's ID who responded for auditing
            return repository.save(review);
        }
        return null; // Or throw an exception if review not found
    }
}