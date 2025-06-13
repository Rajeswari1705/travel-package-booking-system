package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.exception.ResourceNotFoundException;
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
/**
 * Service layer for handling business logic related to Insurance.
 * Called by the controller and interacts with repository.
 */
@Service
public class InsuranceService {
 
    @Autowired
    private InsuranceRepository repo;
 
    /**
     * Saves a new insurance policy to the database.
     * Price and coverage details are auto-set using @PrePersist in the entity.
     */
    public Insurance createInsurance(Insurance insurance) {
        return repo.save(insurance);
    }
 
    /**
     * Returns all insurance records for a given user.
     *
     * @param userId ID of the user
     * @return list of insurance policies
     */
    public List<Insurance> getUserInsurance(Integer userId) {
        return repo.findByUserId(userId);
    }
 
    /**
     * Updates the status of a specific insurance record.
     *
     * @param insuranceId ID of the insurance
     * @param status New status (e.g. Approved, Cancelled)
     * @return Updated Insurance object
     */
    public Insurance updateInsuranceStatus(Integer insuranceId, String status) {
        Insurance insurance = repo.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance ID not found: " + insuranceId));
        insurance.setStatus(status);
        return repo.save(insurance);
    }
}
 