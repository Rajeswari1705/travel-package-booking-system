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

@Service
public class AgentResponseService {
 
    @Autowired
    private AgentResponseRepository agentResponseRepository;
 
    @Autowired
    private ReviewRepository reviewRepository;
 
    @Autowired
    private TravelPackageClient travelPackageClient;
 
    public AgentResponse respondToReview(Long agentId, Long reviewId, String responseMessage) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found."));
     
        Long packageId = review.getPackageId();
     
        Map<String, Long> response = travelPackageClient.getAgentIdByPackageId(packageId);
        Long actualAgentId = response.get("agentId");

        if (!actualAgentId.equals(agentId)) {
            throw new ResourceNotFoundException("Unauthorized: You are not the owner of this package.");
        }

     
        AgentResponse agentresponse = new AgentResponse();
        agentresponse.setReviewId(reviewId);
        agentresponse.setPackageId(packageId);
        agentresponse.setAgentId(agentId);
        agentresponse.setResponseMessage(responseMessage);
        agentresponse.setResponseTime(LocalDateTime.now());
     
        return agentResponseRepository.save(agentresponse);
    }
 
    public List<AgentResponse> getResponsesForReview(Long reviewId) {
        return agentResponseRepository.findByReviewId(reviewId);
    }
    
    public AgentResponse updateResponse(Long responseId, String updatedMessage) {
        AgentResponse response = agentResponseRepository.findById(responseId)
            .orElseThrow(() -> new ResourceNotFoundException("Agent Response not found"));

        response.setResponseMessage(updatedMessage);
        response.setResponseTime(LocalDateTime.now());

        return agentResponseRepository.save(response);
    }
    public void deleteResponse(Long responseId) {
        AgentResponse response = agentResponseRepository.findById(responseId)
            .orElseThrow(() -> new ResourceNotFoundException("Agent Response not found"));

        agentResponseRepository.delete(response);
    }

}