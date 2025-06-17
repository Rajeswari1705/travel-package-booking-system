package com.ratings.review.service;

import com.ratings.review.entity.Review;
import com.ratings.review.exception.ResourceNotFoundException;
import com.ratings.review.repository.ReviewRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
@Service
public class ReviewService {
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Posts a new review.
     */
    public Review postReview(Review review) {
        logger.info("Posting a new review.");
        review.setTimestamp(LocalDateTime.now()); // Sets timestamp before saving
        return reviewRepository.save(review);
    }
    /**
     * Updates an existing review.
     */
    public Review updateReview(Long reviewId, Review updatedReview) {
        logger.info("Updating review ID {}", reviewId);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    logger.error("Review ID {} not found.", reviewId);
                    return new ResourceNotFoundException("Review not found.");
                });

        review.setRating(updatedReview.getRating());
        review.setComment(updatedReview.getComment());

        return reviewRepository.save(review);
    }

    /**
     * Deletes a review.
     */
    public void deleteReview(Long reviewId) {
        logger.info("Deleting review ID {}", reviewId);
        reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    logger.error("Review ID {} not found.", reviewId);
                    return new ResourceNotFoundException("Review not found.");
                });

        reviewRepository.deleteById(reviewId);
    }

    /**
     * Retrieves all reviews for a specific travel package.
     */
    public List<Review> getReviewsByPackage(Long packageId) {
        logger.info("Fetching reviews for package ID {}", packageId);
        return reviewRepository.findAll().stream()
                .filter(review -> review.getPackageId().equals(packageId))
                .toList();
    }
    /**
     * finding average rating for a package
     */
    public double getAverageRatingForPackage(Long packageId) {
        List<Review> reviews = getReviewsByPackage(packageId);
        return reviews.stream()
                      .mapToInt(Review::getRating)
                      .average()
                      .orElse(0.0);
    }

}
