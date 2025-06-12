package com.example.travelinsuranceservice.controller;
 
import com.example.travelinsuranceservice.model.AssistanceRequest;
import com.example.travelinsuranceservice.service.AssistanceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/assistance")
public class AssistanceRequestController {
 
    @Autowired
    private AssistanceRequestService service;
 
    private static final Logger logger = LoggerFactory.getLogger(AssistanceRequestController.class);
 
    @PostMapping
    public ResponseEntity<AssistanceRequest> addRequest(@RequestBody AssistanceRequest request) {
        logger.info("Creating new assistance request for userId: {}", request.getUserId());
        return new ResponseEntity<>(service.createRequest(request), HttpStatus.CREATED);
    }
 
    @GetMapping("/{userId}")
    public List<AssistanceRequest> getRequests(@PathVariable Integer userId) {
        logger.info("Getting all assistance requests for userId: {}", userId);
        return service.getRequestsByUser(userId);
    }
 
    @PutMapping("/{requestId}/status")
    public ResponseEntity<AssistanceRequest> updateStatus(@PathVariable Integer requestId, @RequestParam String status) {
        logger.info("Updating assistance request ID {} to status '{}'", requestId, status);
        AssistanceRequest updated = service.updateStatus(requestId, status);
        if (updated != null)
            return new ResponseEntity<>(updated, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

 