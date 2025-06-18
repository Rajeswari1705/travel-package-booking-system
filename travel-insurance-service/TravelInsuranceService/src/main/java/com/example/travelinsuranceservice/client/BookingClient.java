package com.example.travelinsuranceservice.client;
 
import com.example.travelinsuranceservice.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
/**
* Feign client to communicate with the Booking module.
*/
@FeignClient(name = "booking-service")
public interface BookingClient {
 
    /**
     * Fetch booking details by bookingId from the Booking module.
     * @param bookingId the ID of the booking
     * @return the BookingDTO containing booking information
     */
    @GetMapping("/api/bookings/{id}")
    BookingDTO getBookingById(@PathVariable("id") Integer bookingId);
}