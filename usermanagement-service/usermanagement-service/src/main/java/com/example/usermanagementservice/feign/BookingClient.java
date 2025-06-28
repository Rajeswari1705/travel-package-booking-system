package com.example.usermanagementservice.feign;
 
import com.example.usermanagementservice.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
import java.util.List;
 
// Replace "booking-service" with your actual service name
@FeignClient(name = "TravelBooking-PaymentModule")
public interface BookingClient {
 
    @GetMapping("/api/bookings/internal/bookings/user/{userId}")
    List<BookingDTO> getBookingsByUserId(@PathVariable Long userId);
}