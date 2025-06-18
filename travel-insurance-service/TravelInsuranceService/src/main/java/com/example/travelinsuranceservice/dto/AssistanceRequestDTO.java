package com.example.travelinsuranceservice.dto;
 
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
/**
 * DTO used to receive a new assistance request from the user.
 */
@Data
public class AssistanceRequestDTO {
 
    @NotNull(message = "User ID is required")
    private Integer userId;
 
    @NotNull(message = "Issue description is required")
    private String issueDescription;
}
 
