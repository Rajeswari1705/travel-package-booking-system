package com.ratings.review.service;

import com.ratings.review.client.BookingClient;
import com.ratings.review.client.UserClient;
import com.ratings.review.dto.UserDTO;
import com.ratings.review.entity.Review;
import com.ratings.review.exception.ResourceNotFoundException;
import com.ratings.review.repository.ReviewRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing reviews.
 */
@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private BookingClient bookingClient;

    /**
     * Posts a new review after validating user and booking status.
     *
     * @param review the review to be posted
     * @return the saved review
     */
    public Review postReview(Review review) {
        logger.info("Attempting to post review for user ID {} and package ID {}", review.getUserId(), review.getPackageId());

        UserDTO user = userClient.getUserById(review.getUserId());
        if (user == null || !"CUSTOMER".equalsIgnoreCase(user.getRole())) {
            logger.warn("User ID {} is not a valid customer", review.getUserId());
            throw new ResourceNotFoundException("Only customers can post reviews.");
        }

        boolean completed = bookingClient.hasCompletedBooking(review.getUserId(), review.getPackageId().toString());
        if (!completed) {
            logger.warn("User ID {} has not completed booking for package ID {}", review.getUserId(), review.getPackageId());
            throw new ResourceNotFoundException("Journey not completed for the given package.");
        }

        if (reviewRepository.existsByUserIdAndPackageId(review.getUserId(), review.getPackageId())) {
            logger.warn("Duplicate review attempt by user ID {} for package ID {}", review.getUserId(), review.getPackageId());
            throw new ResourceNotFoundException("You have already reviewed this package.");
        }

        review.setTimestamp(LocalDateTime.now());
        Review savedReview = reviewRepository.save(review);
        logger.info("Review posted successfully.");

        return savedReview;
    }
    /**
     * Updates an existing review.
     *
     * @param reviewId      the ID of the review to update
     * @param updatedReview the updated review data
     * @return the updated review
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

        Review savedReview = reviewRepository.save(review);
        logger.info("Review ID {} updated successfully", reviewId);
        return savedReview;
    }

    /**
     * Deletes a review by ID.
     *
     * @param reviewId the ID of the review to delete
     */
    public void deleteReview(Long reviewId) {
        logger.info("Deleting review ID {}", reviewId);

        reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    logger.error("Review ID {} not found.", reviewId);
                    return new ResourceNotFoundException("Review not found.");
                });

        reviewRepository.deleteById(reviewId);
        logger.info("Review ID {} deleted successfully", reviewId);
    }

    /**
     * Retrieves all reviews for a specific travel package.
     *
     * @param packageId the ID of the travel package
     * @return list of reviews
     */
    public List<Review> getReviewsByPackage(Long packageId) {
        logger.info("Fetching reviews for package ID {}", packageId);
        return reviewRepository.findAll().stream()
                .filter(review -> review.getPackageId().equals(packageId))
                .toList();
    }

    /**
     * Calculates the average rating for a travel package.
     *
     * @param packageId the ID of the travel package
     * @return average rating
     */
    public double getAverageRatingForPackage(Long packageId) {
        List<Review> reviews = getReviewsByPackage(packageId);
        double average = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        logger.info("Average rating for package ID {} is {}", packageId, average);
        return average;
    }
}
