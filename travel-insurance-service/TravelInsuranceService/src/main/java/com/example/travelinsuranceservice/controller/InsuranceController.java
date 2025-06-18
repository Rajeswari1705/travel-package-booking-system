package com.example.travelinsuranceservice.controller;
 
import com.example.travelinsuranceservice.dto.*;
import com.example.travelinsuranceservice.model.CoverageType;
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.service.InsuranceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
 
import java.util.*;
import java.util.stream.Collectors;
 
/**
 * Controller for managing travel insurance APIs.
 */
@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {
 
    // Logger to track method execution and debug
    private static final Logger logger = LoggerFactory.getLogger(InsuranceController.class);
 
    @Autowired
    private InsuranceService service;
 
    /**
     * POST /api/insurance
     * Create new insurance for a user with selected coverage type.
     */
    @PostMapping
    public ResponseEntity<Insurance> createInsurance(@Valid @RequestBody InsuranceRequestDTO dto) {
        logger.info("Creating insurance for userId: {}", dto.getUserId());
        Insurance insurance = service.createInsurance(dto);
        return new ResponseEntity<>(insurance, HttpStatus.CREATED);
    }
 
    /**
     * PUT /api/insurance/{insuranceId}/booking?bookingId=123
     * Attach a bookingId to an existing insurance policy.
     */
    @PutMapping("/{insuranceId}/bookings")
    public ResponseEntity<Insurance> updateBooking(@PathVariable Integer insuranceId,
                                                   @RequestParam Integer bookingId) {
        logger.info("Linking bookingId {} to insuranceId {}", bookingId, insuranceId);
        return ResponseEntity.ok(service.updateBookingId(insuranceId, bookingId));
    }
 
    /**
     * PUT /api/insurance/{insuranceId}/status?status=Cancelled
     * Update the status of an insurance policy.
     */
    @PutMapping("/{insuranceId}/status")
    public ResponseEntity<Insurance> updateStatus(@PathVariable Integer insuranceId,
                                                  @RequestParam String status) {
        logger.info("Updating insurance status for ID {} to {}", insuranceId, status);
        return ResponseEntity.ok(service.updateStatus(insuranceId, status));
    }
 
    /**
     * GET /api/insurance/user/{userId}
     * Fetch all insurance policies for a specific user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Insurance>> getByUser(@PathVariable Integer userId) {
        logger.info("Fetching insurance for userId: {}", userId);
        return ResponseEntity.ok(service.getUserInsurance(userId));
    }
 
    /**
     * GET /api/insurance/coverage-plans
     * Return available insurance coverage options.
     */
    @GetMapping("/coverage-plans")
    public ResponseEntity<List<CoveragePlanDTO>> getCoveragePlans() {
        logger.info("Fetching available coverage plan options.");
        List<CoveragePlanDTO> plans = Arrays.stream(CoverageType.values())
                .map(type -> new CoveragePlanDTO(
                        type.name(),
                        type.getCoverageDetails(),
                        type.getPrice(),
                        type.getClaimableAmount()))
                .collect(Collectors.toList());
 
        return ResponseEntity.ok(plans);
    }
}
 