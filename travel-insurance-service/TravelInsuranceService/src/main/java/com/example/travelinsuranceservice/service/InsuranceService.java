package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.client.BookingClient;
import com.example.travelinsuranceservice.client.UserClient;
import com.example.travelinsuranceservice.dto.*;
import com.example.travelinsuranceservice.exception.*;
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.repository.InsuranceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
/**
 * Business logic for managing Insurance operations.
 */
@Service
public class InsuranceService {
 
    private static final Logger logger = LoggerFactory.getLogger(InsuranceService.class);
 
    @Autowired
    private InsuranceRepository repo;
 
    @Autowired
    private UserClient userClient;
 
    @Autowired
    private BookingClient bookingClient;
 
    /**
     * Creates a new insurance after validating userId via UserClient.
     */
    public Insurance createInsurance(InsuranceRequestDTO dto) {
        logger.info("Validating userId: {}", dto.getUserId());
        if (userClient.getUserById(dto.getUserId()) == null) {
            logger.error("Invalid userId: {}", dto.getUserId());
            throw new InvalidInputException("Invalid user ID: " + dto.getUserId());
        }
 
        Insurance insurance = new Insurance();
        insurance.setUserId(dto.getUserId());
        insurance.setCoverageType(dto.getCoverageType());
 
        logger.info("Saving insurance for userId {}", dto.getUserId());
        return repo.save(insurance);
    }
 
    /**
     * Updates insurance with bookingId after Booking module creates it.
     */
    public Insurance updateBookingId(Integer insuranceId, Integer bookingId) {
        Insurance insurance = repo.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance not found: " + insuranceId));
 
        BookingDTO booking = bookingClient.getBookingById(bookingId);
        if (booking == null) {
            logger.error("Invalid booking ID: {}", bookingId);
            throw new InvalidInputException("Invalid booking ID: " + bookingId);
        }
 
        insurance.setBookingId(bookingId);
        logger.info("Linked bookingId {} to insuranceId {}", bookingId, insuranceId);
        return repo.save(insurance);
    }
 
    /**
     * Updates the status of an insurance policy.
     */
    public Insurance updateStatus(Integer insuranceId, String status) {
        Insurance insurance = repo.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance not found: " + insuranceId));
        insurance.setStatus(status);
        logger.info("Updated insurance status for {} to {}", insuranceId, status);
        return repo.save(insurance);
    }
 
    /**
     * Returns all insurance records for a given user.
     */
    public List<Insurance> getUserInsurance(Integer userId) {
        logger.info("Fetching insurance list for userId: {}", userId);
        return repo.findByUserId(userId);
    }
}
 