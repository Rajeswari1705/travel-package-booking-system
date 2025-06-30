package com.ratings.review.controller;

import com.ratings.review.entity.AgentResponse;
import com.ratings.review.entity.Review;
import com.ratings.review.exception.ResourceNotFoundException;
import com.ratings.review.service.AgentResponseService;
import com.ratings.review.repository.ReviewRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Controller for handling agent responses to reviews.
 * This class provides REST endpoints for agents to respond to customer reviews,
 * retrieve responses, update responses, and delete responses.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/agent-responses")
public class AgentResponseController {

    private static final Logger logger = LoggerFactory.getLogger(AgentResponseController.class);

    @Autowired
    private AgentResponseService agentResponseService;

    @Autowired
    private ReviewRepository reviewRepository;
    /**
     * Handles the creation of a new agent response to a specific review.
     * An agent provides a response message for a customer's review.
     *
     * @param agentId The ID of the agent posting the response.
     * @param reviewId The ID of the review being responded to.
     * @param requestBody A map containing the "responseMessage" string.
     * @return ResponseEntity containing a map with details of the review and the saved agent response,
     * or throws ResourceNotFoundException if the review is not found.
     */
    @PostMapping("/{agentId}/{reviewId}")
    public ResponseEntity<Map<String, Object>> respondToReview(
            @PathVariable Long agentId,
            @PathVariable Long reviewId,
            @RequestBody Map<String, String> requestBody) {
     
        String responseMessage = requestBody.get("responseMessage");
        logger.info("Received request to post response from agentId: {} to reviewId: {} with message: {}", agentId, reviewId, responseMessage);

        AgentResponse savedResponse = agentResponseService.respondToReview(agentId, reviewId, responseMessage);
        logger.debug("Agent response saved successfully: {}", savedResponse);
        
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    logger.error("Review not found for ID: {}", reviewId);
                    return new ResourceNotFoundException("Review not found");
                });
     
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("packageId", review.getPackageId());
        responseBody.put("reviewId", reviewId);
        responseBody.put("reviewMessage", review.getComment());
        responseBody.put("responseMessage", savedResponse.getResponseMessage());
        responseBody.put("responseTime", savedResponse.getResponseTime());
        logger.info("Successfully responded to reviewId: {}", reviewId);
        return ResponseEntity.ok(responseBody);
    }
    /**
     * Retrieves all agent responses for a specific review.
     *
     * @param reviewId The ID of the review for which responses are to be fetched.
     * @return ResponseEntity containing a list of AgentResponse objects.
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<List<AgentResponse>> getResponsesForReview(@PathVariable Long reviewId) {
        logger.info("Fetching responses for Review ID {}", reviewId);
        List<AgentResponse> responses = agentResponseService.getResponsesForReview(reviewId);
        logger.info("Found {} responses for Review ID {}", responses.size(), reviewId);
        return ResponseEntity.ok(responses);
    }
    /**
     * Updates an existing agent response.
     *
     * @param responseId The ID of the agent response to be updated.
     * @param requestBody A map containing the "responseMessage" string with the new message.
     * @return ResponseEntity containing the updated AgentResponse object.
     */
    @PutMapping("/{responseId}")
    public ResponseEntity<AgentResponse> updateAgentResponse(
            @PathVariable Long responseId,
            @RequestBody Map<String, String> requestBody) {

    	String updatedMessage = requestBody.get("responseMessage");
        logger.info("Received request to update response with ID: {} to new message: {}", responseId, updatedMessage);

        AgentResponse updatedResponse = agentResponseService.updateResponse(responseId, updatedMessage);
        logger.info("Agent response with ID: {} updated successfully.", responseId);
        return ResponseEntity.ok(updatedResponse);
    }
    /**
     * Deletes an agent response by its ID.
     *
     * @param responseId The ID of the agent response to be deleted.
     * @return ResponseEntity confirming the deletion.
     */
    @DeleteMapping("/{responseId}")
    public ResponseEntity<Map<String, String>> deleteAgentResponse(@PathVariable Long responseId) {
        logger.info("Received request to delete response with ID: {}", responseId);
        agentResponseService.deleteResponse(responseId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Agent response deleted successfully");
        logger.info("Agent response with ID: {} deleted successfully.", responseId);
        return ResponseEntity.ok(response);
    }

    
}
