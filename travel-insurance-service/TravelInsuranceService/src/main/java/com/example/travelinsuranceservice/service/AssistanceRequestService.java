package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.exception.ResourceNotFoundException;
import com.example.travelinsuranceservice.model.AssistanceRequest;
import com.example.travelinsuranceservice.repository.AssistanceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
/**
 * Service layer for Assistance Request module.
 * Manages creation, fetching, and status updates.
 */
@Service
public class AssistanceRequestService {
 
    @Autowired
    private AssistanceRequestRepository repo;
 
    /**
     * Creates a new assistance request.
     * Timestamp and resolution time are auto-set in the entity.
     *
     * @param request Assistance request object
     * @return persisted AssistanceRequest
     */
    public AssistanceRequest createRequest(AssistanceRequest request) {
        return repo.save(request);
    }
 
    /**
     * Retrieves all assistance requests submitted by a user.
     *
     * @param userId User's ID
     * @return List of assistance requests
     */
    public List<AssistanceRequest> getRequestsByUser(Integer userId) {
        return repo.findByUserId(userId);
    }
 
    /**
     * Updates the status of an existing request (e.g., to 'Resolved').
     *
     * @param requestId ID of the request to update
     * @param status New status value
     * @return Updated AssistanceRequest object
     */
    public AssistanceRequest updateStatus(Integer requestId, String status) {
        AssistanceRequest req = repo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Assistance Request ID not found: " + requestId));
        req.setStatus(status);
        return repo.save(req);
    }
}
 