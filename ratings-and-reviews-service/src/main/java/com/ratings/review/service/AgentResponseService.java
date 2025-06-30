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
import java.util.Map;

/**
 * Service class for managing agent responses to customer reviews.
 * This service handles operations such as posting, retrieving, updating,
 * and deleting agent responses, including necessary validations.
 */
@Service
public class AgentResponseService {
	private static final Logger logger = LoggerFactory.getLogger(AgentResponseService.class);
    @Autowired
    private AgentResponseRepository agentResponseRepository;
 
    @Autowired
    private ReviewRepository reviewRepository;
 
    @Autowired
    private TravelPackageClient travelPackageClient;
    /**
     * Allows an agent to post a response to a specific review.
     * Validates if the agent is authorized to respond to the review's associated package.
     *
     * @param agentId The ID of the agent posting the response.
     * @param reviewId The ID of the review to which the agent is responding.
     * @param responseMessage The content of the agent's response.
     * @return The saved AgentResponse object.
     * @throws ResourceNotFoundException If the review is not found or the agent is unauthorized.
     */
    public AgentResponse respondToReview(Long agentId, Long reviewId, String responseMessage) {
        logger.info("Agent {} attempting to respond to review {}", agentId, reviewId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    logger.error("Review with ID {} not found for agent response.", reviewId);
                    return new ResourceNotFoundException("Review not found.");
                });
        logger.debug("Review found: {}", review);
        
        Long packageId = review.getPackageId();
        logger.debug("Fetching agent ID for package {}", packageId);
        Map<String, Long> response = travelPackageClient.getAgentIdByPackageId(packageId);
        Long actualAgentId = response.get("agentId");
        logger.debug("Actual agent ID for package {} is {}", packageId, actualAgentId);

        if (!actualAgentId.equals(agentId)) {
            logger.warn("Agent {} is unauthorized to respond to package {} (owned by agent {}).", agentId, packageId, actualAgentId);
            throw new ResourceNotFoundException("Unauthorized: You are not the owner of this package.");
        }

        AgentResponse agentresponse = new AgentResponse();
        agentresponse.setReviewId(reviewId);
        agentresponse.setPackageId(packageId);
        agentresponse.setAgentId(agentId);
        agentresponse.setResponseMessage(responseMessage);
        agentresponse.setResponseTime(LocalDateTime.now());
        
        AgentResponse savedResponse = agentResponseRepository.save(agentresponse);
        logger.info("Agent response for review {} saved successfully by agent {}. Response ID: {}", reviewId, agentId, savedResponse.getResponseId());
        return savedResponse;
    }
 
    /**
     * Retrieves all agent responses associated with a specific review.
     *
     * @param reviewId The ID of the review to retrieve responses for.
     * @return A list of AgentResponse objects.
     */
    public List<AgentResponse> getResponsesForReview(Long reviewId) {
        logger.info("Fetching all agent responses for review ID {}", reviewId);
        List<AgentResponse> responses = agentResponseRepository.findByReviewId(reviewId);
        logger.info("Found {} agent responses for review ID {}", responses.size(), reviewId);
        return responses;
    }
    /**
     * Updates the message of an existing agent response.
     *
     * @param responseId The ID of the agent response to update.
     * @param updatedMessage The new message for the response.
     * @return The updated AgentResponse object.
     * @throws ResourceNotFoundException If the agent response is not found.
     */
    public AgentResponse updateResponse(Long responseId, String updatedMessage) {
        logger.info("Attempting to update agent response with ID: {}", responseId);
        AgentResponse response = agentResponseRepository.findById(responseId)
            .orElseThrow(() -> {
                logger.error("Agent Response with ID {} not found for update.", responseId);
                return new ResourceNotFoundException("Agent Response not found");
            });

        String oldMessage = response.getResponseMessage();
        response.setResponseMessage(updatedMessage);
        response.setResponseTime(LocalDateTime.now()); // Update timestamp on modification

        AgentResponse savedResponse = agentResponseRepository.save(response);
        logger.info("Agent response {} updated successfully. Old message: '{}', New message: '{}'", responseId, oldMessage, updatedMessage);
        return savedResponse;
    }

    /**
     * Deletes an agent response by its ID.
     *
     * @param responseId The ID of the agent response to delete.
     * @throws ResourceNotFoundException If the agent response is not found.
     */
    public void deleteResponse(Long responseId) {
        logger.info("Attempting to delete agent response with ID: {}", responseId);
        AgentResponse response = agentResponseRepository.findById(responseId)
            .orElseThrow(() -> {
                logger.error("Agent Response with ID {} not found for deletion.", responseId);
                return new ResourceNotFoundException("Agent Response not found");
            });

        agentResponseRepository.delete(response);
        logger.info("Agent response with ID: {} deleted successfully.", responseId);
    }
}