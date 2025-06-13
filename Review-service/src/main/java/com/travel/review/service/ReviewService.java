package com.travel.review.service;

import com.travel.review.entity.Review;
import java.util.List;

public interface ReviewService {
    Review addReview(Review review);
    List<Review> getReviewsByPackageId(Long packageId);
    List<Review> getReviewsByUserId(Long userId);
    Review updateReview(Long id, Review review);
    void deleteReview(Long id);
    Review addAgentResponseToReview(Long reviewId, String agentResponse); // New method
}