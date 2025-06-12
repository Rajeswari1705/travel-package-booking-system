 package com.example.travelinsuranceservice.model;
 
import jakarta.persistence.*;
import java.time.LocalDateTime;
 
@Entity
public class AssistanceRequest {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;
 
    private Integer userId;
    private String issueDescription;
    private String status;
    private String resolutionTime;
    private LocalDateTime requestTimestamp;
 
    // Getters and Setters
    public Integer getRequestId() {
        return requestId;
    }
 
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }
 
    public Integer getUserId() {
        return userId;
    }
 
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
 
    public String getIssueDescription() {
        return issueDescription;
    }
 
    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public String getResolutionTime() {
        return resolutionTime;
    }
 
    public void setResolutionTime(String resolutionTime) {
        this.resolutionTime = resolutionTime;
    }
 
    public LocalDateTime getRequestTimestamp() {
        return requestTimestamp;
    }
 
    public void setRequestTimestamp(LocalDateTime requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }
}
 