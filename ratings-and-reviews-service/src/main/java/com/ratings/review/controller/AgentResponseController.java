package com.ratings.review.controller;

import com.ratings.review.entity.AgentResponse;
import com.ratings.review.exception.ResourceNotFoundException;
import com.ratings.review.service.AgentResponseService;

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
 */
@RestController
@RequestMapping("/api/agent-responses") //Base mapping
public class AgentResponseController {
    private static final Logger logger = LoggerFactory.getLogger(AgentResponseController.class);

    @Autowired
    private AgentResponseService agentResponseService;

    /**
     * Travel Agent Responds to a Review (Only if they own the package)
     */
    @PostMapping("/{agentId}/{reviewId}")
    public ResponseEntity<Map<String, Object>> respondToReview(
            @PathVariable Long agentId,
            @PathVariable Long reviewId,
            @RequestBody Map<String, String> requestBody) { //  Accept JSON body as a map

        String responseMessage = requestBody.get("responseMessage"); // Extract message from JSON

        //Call the service method correctly
        AgentResponse savedResponse = agentResponseService.respondToReview(agentId, reviewId, responseMessage);

        //Extract packageId and review comment for response
        Long packageId = savedResponse.getReview().getpackageId();
        String reviewMessage = savedResponse.getReview().getComment();

        //Create response with required fields
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("packageId", packageId);
        responseBody.put("reviewId", reviewId);
        responseBody.put("reviewMessage", reviewMessage);
        responseBody.put("responseMessage", savedResponse.getResponseMessage());
        responseBody.put("responseTime", savedResponse.getResponseTime());

        return ResponseEntity.ok(responseBody);
    }

    /**
     * Get Responses for a Specific Review
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<List<AgentResponse>> getResponsesForReview(@PathVariable Long reviewId) {
        logger.info("Fetching responses for Review ID {}", reviewId);
        List<AgentResponse> responses = agentResponseService.getResponsesForReview(reviewId);
        return ResponseEntity.ok(responses);
    }
}
