package com.ratings.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ratings.review.entity.Review;

/**
 * Repository for managing travel package reviews.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
