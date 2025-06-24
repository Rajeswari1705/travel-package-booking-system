package com.ratings.review.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface for communicating with the TravelBooking_PaymentModule service.
 * This client is used to check whether a user has completed a booking for a specific travel package.
 */
@FeignClient(name = "TravelBooking-PaymentModule") // Specifies the name of the service to connect to via Eureka
public interface BookingClient {

    /**
     * Checks if a user has completed a booking for a given package.
     *
     * @param userId    the ID of the user
     * @param packageId the ID of the travel package
     * @return true if the booking is completed, false otherwise
     */
    @GetMapping("/api/bookings/user/{userId}/package/{packageId}/completed") // Maps to the endpoint in the target service
    boolean hasCompletedBooking(@PathVariable("userId") Long userId,
                                @PathVariable("packageId") String packageId);
}
