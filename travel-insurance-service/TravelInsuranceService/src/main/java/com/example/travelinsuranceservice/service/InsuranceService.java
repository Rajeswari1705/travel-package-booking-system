package com.example.travelinsuranceservice.service;

import com.example.travelinsuranceservice.client.BookingClient;
import com.example.travelinsuranceservice.client.UserClient;
import com.example.travelinsuranceservice.dto.BookingDTO;
import com.example.travelinsuranceservice.dto.InsuranceRequestDTO;
import com.example.travelinsuranceservice.exception.InvalidInputException;
import com.example.travelinsuranceservice.exception.ResourceNotFoundException;
import com.example.travelinsuranceservice.model.CoverageType;
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.repository.InsuranceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class handling business logic for Insurance module.
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
     * Creates new insurance only if user doesn't already have one.
     */
    public Insurance createInsurance(InsuranceRequestDTO dto) {
    	logger.info("Creating insurance for userId: {}", dto.getUserId());
 
        // Step 1: Validate user exists (using Feign client)
        if (userClient.getUserById(dto.getUserId()) == null) {
            logger.error("Invalid user ID: {}", dto.getUserId());
            throw new InvalidInputException("Invalid user ID: " + dto.getUserId());
        }
 
        // Step 2: Check if insurance already exists
        List<Insurance> existing = repo.findByUserId(dto.getUserId());
        if (!existing.isEmpty()) {
            logger.warn("Insurance already exists for user ID {}", dto.getUserId());
            throw new InvalidInputException("Insurance already exists for user.");
        }
 
        // Step 3: Create insurance
        Insurance insurance = new Insurance();
        insurance.setUserId(dto.getUserId());
        insurance.setCoverageType(CoverageType.valueOf(dto.getCoverageType().toUpperCase()));
 
        // Note: other values (price, claim, details, status) set automatically via @PrePersist
        Insurance saved = repo.save(insurance);
        logger.info("Insurance created with ID: {}", saved.getInsuranceId());
 
        return saved;
    }

    /**
     * Returns all insurance records for the given userId.
     */
    public List<Insurance> getUserInsurance(Long userId) {
        logger.info("Fetching insurance list for userId: {}", userId);
        return repo.findByUserId(userId);
    }

    /**
     * Returns the price of the first valid insurance selected by the user.
     * Used by Booking module to calculate total cost.
     */
    public double getInsurancePriceByUserId(Long userId) {
        logger.info("Fetching insurance price for userId: {}", userId);

        List<Insurance> insurances = repo.findByUserId(userId);
        if (insurances.isEmpty()) {
            logger.warn("No insurance found for userId {}", userId);
            return 0.0;
        }

        Insurance insurance = insurances.get(0);

        if ("CANCELLED".equalsIgnoreCase(insurance.getIssuanceStatus())) {
            logger.info("Insurance is cancelled for userId {}", userId);
            return 0.0;
        }

        logger.info("Returning insurance price {} for insuranceId {}", insurance.getPrice(), insurance.getInsuranceId());
        return insurance.getPrice();
    }

    /**
     * Called by Booking module after booking/payment is successful.
     * Updates bookingId and sets issuanceStatus = "ISSUED".
     */
    public String updateBookingIdInInsurance(Integer insuranceId, Long bookingId) {
        Insurance insurance = repo.findById(insuranceId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance not found"));

        BookingDTO booking = bookingClient.getBookingById(bookingId);
        if (booking == null) {
            throw new InvalidInputException("Invalid booking ID: " + bookingId);
        }

        insurance.setBookingId(bookingId);
        insurance.setIssuanceStatus("ISSUED");

        repo.save(insurance);

        return "Insurance linked to booking successfully.";
    }

    /**
     * Validates if the given insurance ID exists in the repository.
     */
    public boolean validateInsuranceId(Long insuranceId) {
        return repo.existsByInsuranceId(insuranceId);
    }
}
