package com.ratings.review.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing an agent's response to a review.
 */
@Entity
@Table(name = "agentResponses")
public class AgentResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review; // Links response to the review   


    @Column(name = "agent_id", nullable = false)

    private Long agentId; 

 // Ensures only package owner responds

    @Column(nullable = false, length = 500)
    private String responseMessage;

    @Column(nullable = false)
    private LocalDateTime responseTime;

    // Getters and Setters
    public Long getResponseId() { return responseId; }
    public void setResponseId(Long responseId) { this.responseId = responseId; }

    public Review getReview() { return review; }
    public void setReview(Review review) { this.review = review; }

    public Long getAgentId() { return agentId; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }

    public String getResponseMessage() { return responseMessage; }
    public void setResponseMessage(String responseMessage) { 
        if (responseMessage == null || responseMessage.isEmpty()) {
            throw new IllegalArgumentException("Response message cannot be empty.");
        }
        this.responseMessage = responseMessage; 
    }

    public LocalDateTime getResponseTime() { return responseTime; }
    public void setResponseTime(LocalDateTime responseTime) { this.responseTime = responseTime; }
}
