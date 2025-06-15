package com.ratings.review.service;

import com.ratings.review.client.TravelPackageClient;
import com.ratings.review.dto.TravelPackageDTO;
import com.ratings.review.entity.AgentResponse;
import com.ratings.review.entity.Review;
import com.ratings.review.exception.ResourceNotFoundException;
import com.ratings.review.repository.AgentResponseRepository;
import com.ratings.review.repository.ReviewRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgentResponseService {
    private static final Logger logger = LoggerFactory.getLogger(AgentResponseService.class);

    @Autowired
    private AgentResponseRepository agentResponseRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TravelPackageClient travelPackageClient; // Feign client to communicate with Travel Package module

    /**
     * Allows a travel agent to respond to a review.
     * Only the agent who owns the package can respond.
     */
    public AgentResponse respondToReview(Long agentId, Long reviewId, String responseMessage) {
        logger.info("Attempting to respond to review ID: {}", reviewId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    logger.error("Review ID {} not found.", reviewId);
                    return new ResourceNotFoundException("Review not found.");
                });

        Long packageId = review.getPackageId();

        // Fetch package details from Travel Package Management module
        TravelPackageDTO travelPackage = travelPackageClient.getPackageById(packageId);

        // Authorization check: Only package owner can respond
        if (!travelPackage.getAgentId().equals(agentId)) {
            logger.warn("Unauthorized response attempt by Agent ID {}", agentId);
            throw new ResourceNotFoundException("Unauthorized: Only the package owner can respond.");
        }

        AgentResponse response = new AgentResponse();
        response.setReview(review);
        response.setAgentId(agentId);
        response.setResponseMessage(responseMessage);
        response.setResponseTime(LocalDateTime.now());

        logger.info("Agent ID {} successfully responded to review ID {}", agentId, reviewId);
        return agentResponseRepository.save(response);
    }

    /**
     * Retrieves responses for a specific review.
     */
    public List<AgentResponse> getResponsesForReview(Long reviewId) {
        logger.info("Fetching responses for review ID: {}", reviewId);
        return agentResponseRepository.findByReview_ReviewId(reviewId);
    }
}
