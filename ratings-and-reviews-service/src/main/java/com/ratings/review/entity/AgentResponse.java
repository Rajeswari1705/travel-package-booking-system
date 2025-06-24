package com.ratings.review.entity;
 
import jakarta.persistence.*;
import java.time.LocalDateTime;
 
@Entity
@Table(name = "agent_responses")
public class AgentResponse {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;
 
    @Column(nullable = false)
    private Long reviewId;
 
    @Column(nullable = false)
    private Long packageId;
 
    @Column(nullable = false)
    private Long agentId;
 
    @Column(nullable = false, length = 500)
    private String responseMessage;
 
    @Column(nullable = false)
    private LocalDateTime responseTime;
 
    // Getters and Setters
    public Long getResponseId() { return responseId; }
    public void setResponseId(Long responseId) { this.responseId = responseId; }
 
    public Long getReviewId() { return reviewId; }
    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }
 
    public Long getPackageId() { return packageId; }
    public void setPackageId(Long packageId) { this.packageId = packageId; }
 
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