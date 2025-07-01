package com.ratings.review.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.AllArgsConstructor; // Add this import
import lombok.NoArgsConstructor;  // Add this import, often good for JPA

@Data
@AllArgsConstructor // Generates constructor with all fields
@NoArgsConstructor  // Generates a no-argument constructor
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
 
    
}