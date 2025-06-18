package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.client.UserClient;
import com.example.travelinsuranceservice.dto.AssistanceRequestDTO;
import com.example.travelinsuranceservice.exception.*;
import com.example.travelinsuranceservice.model.AssistanceRequest;
import com.example.travelinsuranceservice.repository.AssistanceRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
/**
 * Business logic for managing Assistance requests.
 */
@Service
public class AssistanceRequestService {
 
    private static final Logger logger = LoggerFactory.getLogger(AssistanceRequestService.class);
 
    @Autowired
    private AssistanceRequestRepository repo;
 
    @Autowired
    private UserClient userClient;
 
    /**
     * Validates user and creates a new assistance request.
     */
    public AssistanceRequest createRequest(AssistanceRequestDTO dto) {
        logger.info("Validating userId: {}", dto.getUserId());
        if (userClient.getUserById(dto.getUserId()) == null) {
            logger.error("Invalid userId: {}", dto.getUserId());
            throw new InvalidInputException("Invalid user ID: " + dto.getUserId());
        }
 
        AssistanceRequest request = new AssistanceRequest();
        request.setUserId(dto.getUserId());
        request.setIssueDescription(dto.getIssueDescription());
 
        logger.info("Saving new assistance request for userId: {}", dto.getUserId());
        return repo.save(request);
    }
 
    /**
     * Updates the status of an existing assistance request.
     */
    public AssistanceRequest updateStatus(Integer requestId, String status) {
        AssistanceRequest req = repo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found: " + requestId));
        req.setStatus(status);
        logger.info("Updated status for requestId {} to {}", requestId, status);
        return repo.save(req);
    }
 
    /**
     * Fetches all assistance records for a specific user.
     */
    public List<AssistanceRequest> getByUser(Integer userId) {
        logger.info("Fetching assistance list for userId: {}", userId);
        return repo.findByUserId(userId);
    }
}
 