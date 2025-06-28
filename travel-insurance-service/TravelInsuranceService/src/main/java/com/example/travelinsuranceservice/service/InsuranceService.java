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
import java.util.Optional;

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
     * Creates new insurance entry for a user
     * 			
     */
    public Insurance createInsurance(InsuranceRequestDTO dto) {
    	logger.info("Creating insurance for userId: {}, coverageType: {}", dto.getUserId(), dto.getCoverageType());
    	 
    	        // Step 1: Validate user via UserClient (Feign)
    	        if (userClient.getUserById(dto.getUserId()) == null) {
    	            throw new InvalidInputException("Invalid user ID: " + dto.getUserId());
    	        }
    	 
    	        // Step 2: If bookingId is present, check for duplicate insurance for that booking
    	        if (dto.getBookingId() != null) {
    	            Optional<Insurance> existing = repo.findByBookingId(dto.getBookingId());
    	            if (existing.isPresent()) {
    	                throw new InvalidInputException("Insurance already exists for this booking.");
    	            }
    	        } 
    	        else {
    	            // Step 3: If bookingId is not present (user hasn't booked yet), block if they already selected insurance
    	            if (!repo.findByUserIdAndBookingIdIsNull(dto.getUserId()).isEmpty()) {
    	                throw new InvalidInputException("You already have a pending insurance. Please complete booking first.");
    	            }
    	        }
    	 
    	        // Step 4: Create Insurance entity with selected coverage
    	        Insurance insurance = new Insurance();
    	        insurance.setUserId(dto.getUserId());
    	 
    	        // Only set bookingId if present (initially it may be null)
    	        if (dto.getBookingId() != null) {
    	            insurance.setBookingId(dto.getBookingId());
    	        }
    	 
    	        // Set coverage fields from enum
    	        CoverageType type = CoverageType.valueOf(dto.getCoverageType().toUpperCase());
    	        insurance.setCoverageType(type);
    	        insurance.setCoverageDetails(type.getCoverageDetails());
    	        insurance.setPrice(type.getPrice());
    	        insurance.setClaimableAmount(type.getClaimableAmount());
    	 
    	        insurance.setIssuanceStatus("PENDING"); // initial status
    	 
    	        // Step 5: Save and return
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
    
    public double getInsurancePriceByInsuranceId(Integer insuranceId) {
        logger.info("Fetching insurance price for insuranceId: {}", insuranceId);

        Insurance insurance = repo.findById(insuranceId).orElse(null);

        if (insurance == null) {
            logger.warn("No insurance found for insuranceId {}", insuranceId);
            return 0.0;
        }

        if ("CANCELLED".equalsIgnoreCase(insurance.getIssuanceStatus())) {
            logger.info("Insurance is cancelled for insuranceId {}", insuranceId);
            return 0.0;
        }

        logger.info("Returning insurance price {} for insuranceId {}", insurance.getPrice(), insuranceId);
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
    public boolean validateInsuranceId(Integer insuranceId) {
        return repo.existsByInsuranceId(insuranceId);
    }
}


