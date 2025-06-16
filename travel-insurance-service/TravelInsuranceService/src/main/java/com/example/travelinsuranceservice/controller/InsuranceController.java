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
 * REST Controller to expose API endpoints related to Travel Insurance.
 * Includes endpoints for creation, status update, booking ID attachment, and retrieval.
 */
@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {
 
    // Logger to capture request-level info
    private static final Logger logger = LoggerFactory.getLogger(InsuranceController.class);
 
    // Injecting the InsuranceService bean to call business logic
    @Autowired
    private InsuranceService service;
 
    /**
     * POST /api/insurance
     * Creates a new insurance policy for a user.
     * bookingId is not required at this stage and can be null.
     *
     * @param insurance JSON body containing userId and coverageType
     * @return Created insurance record
     */
    @PostMapping
    public ResponseEntity<Insurance> addInsurance(@Valid @RequestBody Insurance insurance) {
        logger.info("Creating insurance for userId: {}", insurance.getUserId());
        Insurance created = service.createInsurance(insurance);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
 
    /**
     * GET /api/insurance/{userId}
     * Fetches all insurance records linked to a particular user.
     *
     * @param userId ID of the user
     * @return List of insurance records
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Insurance>> getByUser(@PathVariable Integer userId) {
        logger.info("Fetching insurance for userId: {}", userId);
        List<Insurance> insurances = service.getUserInsurance(userId);
        return ResponseEntity.ok(insurances);
    }
 
    /**
     * PUT /api/insurance/{insuranceId}/status?status=CANCELLED
     * Updates the status of a specific insurance record.
     *
     * @param insuranceId ID of the insurance to be updated
     * @param status New status (e.g., "APPROVED", "CANCELLED")
     * @return Updated insurance record
     */
    @PutMapping("/{insuranceId}/status")
    public ResponseEntity<Insurance> updateStatus(
            @PathVariable Integer insuranceId,
            @RequestParam String status) {
        logger.info("Updating insurance status for ID {} to {}", insuranceId, status);
        Insurance updated = service.updateInsuranceStatus(insuranceId, status);
        return ResponseEntity.ok(updated);
    }
 
    /**
     * PUT /api/insurance/{insuranceId}/booking?bookingId=123
     * Attaches a bookingId to the insurance after the booking is confirmed.
     * This is typically called by the Booking module after booking creation.
     *
     * @param insuranceId ID of the insurance to update
     * @param bookingId ID of the associated booking
     * @return Updated insurance record with bookingId set
     */
    @PutMapping("/{insuranceId}/booking")
    public ResponseEntity<Insurance> attachBookingId(
            @PathVariable Integer insuranceId,
            @RequestParam Integer bookingId) {
        logger.info("Attaching bookingId {} to insuranceId {}", bookingId, insuranceId);
        Insurance updated = service.updateBookingId(insuranceId, bookingId);
        return ResponseEntity.ok(updated);
    }
}
 