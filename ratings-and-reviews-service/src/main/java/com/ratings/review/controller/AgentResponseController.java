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
 */
@RestController
@RequestMapping("/api/agent-responses")
public class AgentResponseController {

    private static final Logger logger = LoggerFactory.getLogger(AgentResponseController.class);

    @Autowired
    private AgentResponseService agentResponseService;

    @Autowired
    private ReviewRepository reviewRepository;

    @PostMapping("/{agentId}/{reviewId}")
    public ResponseEntity<Map<String, Object>> respondToReview(
            @PathVariable Long agentId,
            @PathVariable Long reviewId,
            @RequestBody Map<String, String> requestBody) {
     
        String responseMessage = requestBody.get("responseMessage");
        AgentResponse savedResponse = agentResponseService.respondToReview(agentId, reviewId, responseMessage);
     
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
     
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("packageId", review.getPackageId());
        responseBody.put("reviewId", reviewId);
        responseBody.put("reviewMessage", review.getComment());
        responseBody.put("responseMessage", savedResponse.getResponseMessage());
        responseBody.put("responseTime", savedResponse.getResponseTime());
     
        return ResponseEntity.ok(responseBody);
    }
     
    @GetMapping("/{reviewId}")
    public ResponseEntity<List<AgentResponse>> getResponsesForReview(@PathVariable Long reviewId) {
        logger.info("Fetching responses for Review ID {}", reviewId);
        List<AgentResponse> responses = agentResponseService.getResponsesForReview(reviewId);
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{responseId}")
    public ResponseEntity<AgentResponse> updateAgentResponse(
            @PathVariable Long responseId,
            @RequestBody Map<String, String> requestBody) {

        String updatedMessage = requestBody.get("responseMessage");
        AgentResponse updatedResponse = agentResponseService.updateResponse(responseId, updatedMessage);

        return ResponseEntity.ok(updatedResponse);
    }
    @DeleteMapping("/{responseId}")
    public ResponseEntity<Map<String, String>> deleteAgentResponse(@PathVariable Long responseId) {
        agentResponseService.deleteResponse(responseId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Agent response deleted successfully");
        return ResponseEntity.ok(response);
    }

    
}
