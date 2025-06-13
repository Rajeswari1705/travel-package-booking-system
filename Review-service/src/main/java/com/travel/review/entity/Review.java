package com.travel.review.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private Long userId;
    private Long packageId;
    private int rating;
    private String comment;
    private LocalDateTime timestamp;

    @Column(length = 1000) // Assuming agent response can be longer
    private String agentResponse; // New field for travel agent's response
    private LocalDateTime agentResponseTimestamp; // Timestamp for agent's response

    @PrePersist
    public void prePersist() {
        setTimestamp(LocalDateTime.now());
    }

    // Getters and Setters for all fields

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAgentResponse() {
        return agentResponse;
    }

    public void setAgentResponse(String agentResponse) {
        this.agentResponse = agentResponse;
    }

    public LocalDateTime getAgentResponseTimestamp() {
        return agentResponseTimestamp;
    }

    public void setAgentResponseTimestamp(LocalDateTime agentResponseTimestamp) {
        this.agentResponseTimestamp = agentResponseTimestamp;
    }
}