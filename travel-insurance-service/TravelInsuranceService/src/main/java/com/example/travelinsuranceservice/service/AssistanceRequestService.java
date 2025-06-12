package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.model.AssistanceRequest;
import com.example.travelinsuranceservice.repository.AssistanceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.time.LocalDateTime;
import java.util.List;
 
@Service
public class AssistanceRequestService {
 
    @Autowired
    private AssistanceRequestRepository repo;
 
    public AssistanceRequest createRequest(AssistanceRequest request) {
        request.setRequestTimestamp(LocalDateTime.now()); // Auto-assign timestamp
        return repo.save(request);
    }
 
    public List<AssistanceRequest> getRequestsByUser(Integer userId) {
        return repo.findByUserId(userId);
    }
 
    public AssistanceRequest updateStatus(Integer requestId, String newStatus) {
        AssistanceRequest ar = repo.findById(requestId).orElse(null);
        if (ar != null) {
            ar.setStatus(newStatus);
            return repo.save(ar);
        }
        return null;
    }
}
 