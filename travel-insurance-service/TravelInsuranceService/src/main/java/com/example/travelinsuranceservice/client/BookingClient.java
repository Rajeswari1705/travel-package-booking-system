package com.example.travelinsuranceservice.client;
 
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
/**
 * Feign client to communicate with the Booking and Payment service.
 * Uses Eureka discovery with service name 'booking-service'.
 */
@FeignClient(name = "TravelBooking_PaymentModule")
public interface BookingClient {
 
    /**
     * Calls the Booking service to verify if the provided booking ID exists.
     *
     * @param bookingId the ID of the booking to validate
     * @return true if the booking exists, false otherwise
     */
    @GetMapping("/api/bookings/validate/{bookingId}")
    boolean isBookingValid(@PathVariable("bookingId") Integer bookingId);
}

 
