package com.example.travelinsuranceservice.dto;
 
import com.example.travelinsuranceservice.model.CoverageType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
/**
 * DTO used to receive insurance creation requests.
 * Sent by the Booking module or frontend when a user selects a coverage plan.
 */
@Data
public class InsuranceRequestDTO {
 
    @NotNull(message = "User ID is required")
    private Integer userId;
 
    @NotNull(message = "Coverage type is required")
    private CoverageType coverageType;
}
 