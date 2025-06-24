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
 * Controller exposing REST endpoints for insurance operations.
 */
@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {
 
    private static final Logger logger = LoggerFactory.getLogger(InsuranceController.class);
 
    @Autowired
    private InsuranceService service;
 
    /**
     * POST /api/insurance
     * Creates new insurance with fixed status "ISSUED".
     */
    @PostMapping
    public ResponseEntity<Insurance> createInsurance(@Valid @RequestBody InsuranceRequestDTO dto) {
        logger.info("POST /api/insurance - creating insurance");
        Insurance insurance = service.createInsurance(dto);
        return new ResponseEntity<>(insurance, HttpStatus.CREATED);
    }
 
    /**
     * PUT /api/insurance/internal/{insuranceId}/booking?bookingId={id}
     * Internal-only endpoint used by Booking module to update booking ID.
     */
    @PutMapping("/internal/{insuranceId}/booking")
    public ResponseEntity<Insurance> updateBookingFromBookingModule(
            @PathVariable Integer insuranceId,
            @RequestParam Long bookingId) {
 
        logger.info("PUT /api/insurance/internal/{}/booking?bookingId={} - Called from Booking service",
                insuranceId, bookingId);
        return ResponseEntity.ok(service.updateBookingId(insuranceId, bookingId));
    }
 
    /**
     * GET /api/insurance/user/{userId}
     * Fetches insurance policies for a given user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Insurance>> getByUser(@PathVariable Integer userId) {
        logger.info("GET /api/insurance/user/{} - Fetching insurance list", userId);
        return ResponseEntity.ok(service.getUserInsurance(userId));
    }
 
    /**
     * GET /api/insurance/coverage-plans
     * Returns available coverage options (name, price, details, claim).
     */
    @GetMapping("/coverage-plans")
    public ResponseEntity<List<CoveragePlanDTO>> getCoveragePlans() {
        logger.info("GET /api/insurance/coverage-plans - Fetching all coverage plans");
 
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

 