package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.client.UserClient;
import com.example.travelinsuranceservice.exception.InvalidInputException;
import com.example.travelinsuranceservice.exception.ResourceNotFoundException;
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
/**
 * Service layer that handles all business logic related to Insurance operations.
 * This class is used by the controller to separate logic from HTTP handling.
 */
@Service
public class InsuranceService {
 
    @Autowired
    private InsuranceRepository repo;
 
    @Autowired
    private UserClient userClient;
 
    /**
     * Creates a new insurance record after validating userId using Feign client.
     * bookingId is not required during creation.
     *
     * @param insurance The insurance object sent from controller
     * @return Saved insurance object in database
     * @throws InvalidInputException if userId is not valid
     */
    public Insurance createInsurance(Insurance insurance) {
        if (!userClient.isUserValid(insurance.getUserId())) {
            throw new InvalidInputException("Invalid user ID: " + insurance.getUserId());
        }
        return repo.save(insurance);
    }
 
    /**
     * Fetches all insurance records associated with a specific userId.
     *
     * @param userId ID of the user
     * @return List of insurance records for the user
     */
    public List<Insurance> getUserInsurance(Integer userId) {
        return repo.findByUserId(userId);
    }
 
    /**
     * Updates the insurance status like "APPROVED", "CANCELLED", etc.
     *
     * @param insuranceId ID of the insurance to update
     * @param status New status to set
     * @return Updated insurance object
     * @throws ResourceNotFoundException if insuranceId is invalid
     */
    public Insurance updateInsuranceStatus(Integer insuranceId, String status) {
        Insurance insurance = repo.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance ID not found: " + insuranceId));
        insurance.setStatus(status);
        return repo.save(insurance);
    }
 
    /**
     * Sets the bookingId field after the booking is created in Booking module.
     *
     * @param insuranceId ID of the insurance to attach booking to
     * @param bookingId The newly created booking ID
     * @return Insurance with updated bookingId
     * @throws ResourceNotFoundException if insuranceId is invalid
     */
    public Insurance updateBookingId(Integer insuranceId, Integer bookingId) {
        Insurance insurance = repo.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance ID not found: " + insuranceId));
        insurance.setBookingId(bookingId);
        return repo.save(insurance);
    }
}
 