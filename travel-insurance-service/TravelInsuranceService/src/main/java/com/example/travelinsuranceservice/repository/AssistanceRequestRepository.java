package com.example.travelinsuranceservice.repository;
 
import com.example.travelinsuranceservice.model.AssistanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface AssistanceRequestRepository extends JpaRepository<AssistanceRequest, Integer> {
    List<AssistanceRequest> findByUserId(Integer userId);
}
 