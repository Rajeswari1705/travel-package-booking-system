package com.booking.controller;

import com.booking.entity.Booking;
import com.booking.DTO.TravelPackageDTO;
import com.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping
    public List<Booking> getAll() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

   @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }

    @GetMapping("/packages/search/title")
    public List<TravelPackageDTO> searchPackagesByTitle(@RequestParam String title) {
        return bookingService.findPackagesByTitle(title);
    }

    @GetMapping("/packages/search/price")
    public List<TravelPackageDTO> searchPackagesByPrice(@RequestParam double maxPrice) {
        return bookingService.findPackagesByPrice(maxPrice);
    }
}
