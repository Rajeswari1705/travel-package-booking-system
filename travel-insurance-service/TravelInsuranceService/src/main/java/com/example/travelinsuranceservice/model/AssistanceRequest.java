package com.example.travelinsuranceservice.model;
 
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import java.time.LocalDateTime;
 
/**
 * Entity class mapped to 'assistance_request' table.
 * Auto-generates request timestamp and has fixed resolution time.
 */
@Entity
@Data
@NoArgsConstructor
public class AssistanceRequest {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId; // Auto-generated primary key
 
    @NotNull(message = "User ID cannot be null")
    private Long userId; // Provided by external module
 
    @NotNull(message = "Issue description cannot be empty")
    private String issueDescription; // Reason for assistance (e.g. Lost passport)
 
    private String status = "Pending"; // Default status at creation
 
    private String resolutionTime = "24 hours"; // Always fixed
 
    private LocalDateTime requestTimestamp; // Date + time of request
 
    /**
     * Automatically sets the timestamp before insertion.
     */
    @PrePersist
    public void setRequestTimestamp() {
        this.requestTimestamp = LocalDateTime.now();
    }
}
 