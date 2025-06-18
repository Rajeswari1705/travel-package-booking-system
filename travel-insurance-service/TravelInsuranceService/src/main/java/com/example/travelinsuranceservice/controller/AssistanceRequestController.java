package com.example.travelinsuranceservice.controller;
 
import com.example.travelinsuranceservice.dto.AssistanceRequestDTO;
import com.example.travelinsuranceservice.model.AssistanceRequest;
import com.example.travelinsuranceservice.service.AssistanceRequestService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
/**
 * Controller for handling assistance-related requests.
 */
@RestController
@RequestMapping("/api/assistance")
public class AssistanceRequestController {
 
    private static final Logger logger = LoggerFactory.getLogger(AssistanceRequestController.class);
 
    @Autowired
    private AssistanceRequestService service;
 
    /**
     * POST /api/assistance
     * Create a new assistance request for a user.
     */
    @PostMapping
    public ResponseEntity<AssistanceRequest> requestHelp(@Valid @RequestBody AssistanceRequestDTO dto) {
        logger.info("Creating assistance request for userId: {}", dto.getUserId());
        AssistanceRequest request = service.createRequest(dto);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }
 
    /**
     * PUT /api/assistance/{requestId}/status?status=Resolved
     * Update the status of an assistance request.
     */
    @PutMapping("/{requestId}/status")
    public ResponseEntity<AssistanceRequest> updateStatus(@PathVariable Integer requestId,
                                                   @RequestParam String status) {
        logger.info("Updating assistance status for requestId {} to {}", requestId, status);
        return ResponseEntity.ok(service.updateStatus(requestId, status));
    }
 
    /**
     * GET /api/assistance/user/{userId}
     * Fetch all assistance requests made by a user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssistanceRequest>> getByUser(@PathVariable Integer userId) {
        logger.info("Fetching assistance requests for userId: {}", userId);
        return ResponseEntity.ok(service.getByUser(userId));
    }
}
 