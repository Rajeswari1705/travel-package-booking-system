package com.ratings.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ratings.review.entity.AgentResponse;

import java.util.List;

/**
 * Repository for managing agent responses to reviews.
 */
@Repository
public interface AgentResponseRepository extends JpaRepository<AgentResponse, Long> {
    /**
     * Find all responses related to a specific review.
     */
    List<AgentResponse> findByReviewId(Long reviewId);
}
