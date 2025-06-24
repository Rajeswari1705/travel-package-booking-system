package com.example.travelinsuranceservice.repository;
 
import com.example.travelinsuranceservice.model.AssistanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 
/**
 * Repository interface for AssistanceRequest entity.
 * Provides CRUD operations and custom method to search by userId.
 */
public interface AssistanceRequestRepository extends JpaRepository<AssistanceRequest, Integer> {
 
    /**
     * Returns all assistance requests submitted by a specific user.
     *
     * @param userId ID of the user
     * @return List of AssistanceRequest objects
     */
    List<AssistanceRequest> findByUserId(Long userId);
}
 