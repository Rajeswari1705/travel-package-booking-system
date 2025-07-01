package com.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Feign client for interacting with the Travel Insurance Service.
 * 
 * This client provides methods to retrieve insurance prices, validate insurance,
 * and update booking IDs for insurance records.
 */
@FeignClient(name = "TravelInsuranceService", url = "http://localhost:8085")
public interface TravelInsuranceClient {

    /**
     * Retrieve the insurance price for a specific user.
     * 
     * @param userId The ID of the user.
     * @return The insurance price for the user.
     */
    @GetMapping("/api/insurance/price/user/{userId}")
    double getInsurancePriceByUserId(@PathVariable("userId") Long userId);

    /**
     * Retrieve the insurance price for a specific insurance ID.
     * 
     * @param insuranceId The ID of the insurance.
     * @return The insurance price for the insurance ID.
     */
    @GetMapping("/api/insurance/price/insurance/{insuranceId}")
    double getInsurancePriceByInsuranceId(@PathVariable("insuranceId") Integer insuranceId);

    /**
     * Validate the insurance by its ID.
     * 
     * @param insuranceId The ID of the insurance to be validated.
     * @return true if the insurance is valid, false otherwise.
     */
    @GetMapping("/api/insurance/validate/{insuranceId}")
    boolean validateInsurance(@PathVariable("insuranceId") Integer insuranceId);

    /**
     * Update the booking ID for a specific insurance record.
     * 
     * @param insuranceId The ID of the insurance.
     * @param bookingId The ID of the booking.
     * @return A message indicating the result of the update operation.
     */
    @PutMapping("/api/insurance/{insuranceId}/booking/{bookingId}")
    String updateInsuranceBookingId(@PathVariable("insuranceId") Integer insuranceId, @PathVariable("bookingId") Long bookingId);
}
