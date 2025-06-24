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
 * Service layer for handling all insurance-related logic.
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
     * Creates new insurance for the given user and coverage type.
     * Automatically sets issuanceStatus to "ISSUED".
     */
    public Insurance createInsurance(InsuranceRequestDTO dto) {
        logger.info("Creating insurance for userId: {}", dto.getUserId());
 
        // Validate user existence using Feign client
        if (userClient.getUserById(dto.getUserId()) == null) {
            logger.error("Invalid user ID: {}", dto.getUserId());
            throw new InvalidInputException("Invalid user ID: " + dto.getUserId());
        }
 
        Insurance insurance = new Insurance();
        insurance.setUserId(dto.getUserId());
        insurance.setCoverageType(dto.getCoverageType());
        // issuanceStatus and other values set in entity via @PrePersist
 
        Insurance saved = repo.save(insurance);
        logger.info("Insurance created with ID: {}", saved.getInsuranceId());
        return saved;
    }
 
    /**
     * Updates the booking ID of a specific insurance,
     * triggered automatically by Booking module (via Feign).
     */
    public Insurance updateBookingId(Integer insuranceId, Long bookingId) {
        logger.info("Updating insurance {} with bookingId {}", insuranceId, bookingId);
 
        Insurance insurance = repo.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance not found: " + insuranceId));
 
        // Validate booking exists
        if (bookingClient.getBookingById(bookingId) == null) {
            logger.error("Invalid booking ID: {}", bookingId);
            throw new InvalidInputException("Invalid booking ID: " + bookingId);
        }
 
        insurance.setBookingId(bookingId);
        Insurance updated = repo.save(insurance);
        logger.info("Insurance {} updated with bookingId {}", insuranceId, bookingId);
        return updated;
    }
 
    /**
     * Returns all insurance policies linked to a specific user.
     */
    public List<Insurance> getUserInsurance(Integer userId) {
        logger.info("Fetching insurance list for userId: {}", userId);
        return repo.findByUserId(userId);
    }
} 
 