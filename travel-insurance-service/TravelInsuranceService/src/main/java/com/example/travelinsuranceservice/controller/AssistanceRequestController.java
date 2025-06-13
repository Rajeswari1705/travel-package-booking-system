package com.example.travelinsuranceservice.controller;
 
import com.example.travelinsuranceservice.model.AssistanceRequest;
import com.example.travelinsuranceservice.service.AssistanceRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.util.List;
 
/**
 * REST Controller that handles endpoints for assistance requests.
 * Routes: /api/assistance → create, get, update assistance requests.
 */
@RestController
@RequestMapping("/api/assistance")
public class AssistanceRequestController {
 
    // Logger for this controller
    private static final Logger logger = LoggerFactory.getLogger(AssistanceRequestController.class);
 
    // Inject the assistance request service
    @Autowired
    private AssistanceRequestService service;
 
    /**
     * POST endpoint to create a new assistance request.
     * Resolution time is auto-set to 24 hours.
     *
     * @param request JSON body representing the request
     * @return created AssistanceRequest with 201 status
     */
    @PostMapping
    public ResponseEntity<AssistanceRequest> addRequest(@Valid @RequestBody AssistanceRequest request) {
        logger.info("Creating assistance request for userId: {}", request.getUserId());
        return new ResponseEntity<>(service.createRequest(request), HttpStatus.CREATED);
    }
 
    /**
     * GET endpoint to fetch all requests for a specific user.
     *
     * @param userId path variable (user ID)
     * @return list of AssistanceRequest objects
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<AssistanceRequest>> getRequests(@PathVariable Integer userId) {
        logger.info("Fetching assistance requests for userId: {}", userId);
        return ResponseEntity.ok(service.getRequestsByUser(userId));
    }
 
    /**
     * PUT endpoint to update the status of an assistance request.
     * Common statuses: Pending, In Progress, Resolved.
     *
     * @param requestId assistance request ID
     * @param status new status value
     * @return updated object or 404 if request not found
     */
    @PutMapping("/{requestId}/status")
    public ResponseEntity<AssistanceRequest> updateStatus(
            @PathVariable Integer requestId,
            @RequestParam String status) {
        logger.info("Updating assistance request ID {} to status: {}", requestId, status);
        AssistanceRequest updated = service.updateStatus(requestId, status);
        return ResponseEntity.ok(updated);
    }
}

 