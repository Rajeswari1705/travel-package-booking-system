package com.example.travelinsuranceservice.controller;
 
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.service.InsuranceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.util.List;
 
/**
 * REST Controller that handles Insurance-related endpoints.
 * Routes: /api/insurance → create, fetch, update insurance policies.
 */
@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {
 
    // Logger to track controller actions
    private static final Logger logger = LoggerFactory.getLogger(InsuranceController.class);
 
    // Inject the service to handle business logic
    @Autowired
    private InsuranceService service;
 
    /**
     * POST endpoint to create a new insurance entry.
     * The coverage type determines the price, provider, and claimable amount.
     *
     * @param insurance request body with insurance data
     * @return created insurance object with 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Insurance> addInsurance(@Valid @RequestBody Insurance insurance) {
        logger.info("Creating insurance for userId: {}", insurance.getUserId());
        return new ResponseEntity<>(service.createInsurance(insurance), HttpStatus.CREATED);
    }
 
    /**
     * GET endpoint to fetch all insurance policies for a specific user.
     *
     * @param userId path variable representing user ID
     * @return list of insurance objects
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Insurance>> getByUser(@PathVariable Integer userId) {
        logger.info("Fetching insurances for userId: {}", userId);
        return ResponseEntity.ok(service.getUserInsurance(userId));
    }
 
    /**
     * PUT endpoint to update insurance status (e.g., Approved, Cancelled).
     *
     * @param insuranceId insurance ID to update
     * @param status new status value (via query param)
     * @return updated insurance object or 404 if not found
     */
    @PutMapping("/{insuranceId}/status")
    public ResponseEntity<Insurance> updateStatus(
            @PathVariable Integer insuranceId,
            @RequestParam String status) {
        logger.info("Updating insurance ID {} to status: {}", insuranceId, status);
        Insurance updated = service.updateInsuranceStatus(insuranceId, status);
        return ResponseEntity.ok(updated);
    }
}

 