package com.booking.controller;
 
import com.booking.dto.BookingDTO;
import com.booking.entity.Booking;
import com.booking.service.BookingService;
import com.booking.response.ApiResponse;
import com.booking.dto.TravelPackageDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.logging.Logger;
 
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
 
    private final BookingService service;
    private static final Logger logger = Logger.getLogger(BookingController.class.getName());
 
    @Autowired
    public BookingController(BookingService service) {
        this.service = service;
    }
    
    /**
     * Create a Booking
     */
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody Booking booking) {
        try {
            BookingDTO bookingDTO = service.createBooking(booking);
            return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            logger.severe("Error creating booking: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    /**
     * To get all Bookings
     */
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = service.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
    
    /**
     * To get booking by Booking ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = service.getBookingById(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }
    
    /**
     * Cancel booking by Booking ID
     */
    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        return service.cancelBooking(id);
    }
    
    /**
     * Delete booking by Booking ID
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteBooking(id);
    }
    
    /**
     * User Management Module to get bookings by User ID
     */
    @GetMapping("/internal/bookings/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
    	List<Booking> bookings = service.getBookingsByUserId(userId);
    	if (bookings.isEmpty()) {
    		return ResponseEntity.notFound().build();
    		}
    	return ResponseEntity.ok(bookings);
    	}

    
    /**
     * Reviews and Rating Module to validate booking
     */
    @GetMapping("/user/{userId}/package/{packageId}/completed")
    public boolean hasUserCompletedPackage(@PathVariable Long userId, @PathVariable String packageId) {
        return service.hasUserCompletedPackage(userId, packageId);
    }
    
    /**
     * Travel Insurance module to get Booking ID
     */
    @GetMapping("/internal/{id}")
    public ResponseEntity<Booking> getInternalBookingById(@PathVariable Long id) {
        Booking booking = service.getBookingById(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }
     
    /**
     * To get All packages from Travel Package Management Module
     */
    @GetMapping("/packages")
    public ResponseEntity<ApiResponse> getAllPackages() {
        List<TravelPackageDTO> packages = service.getAllPackages();
        return ResponseEntity.ok(new ApiResponse(true, "All packages retrieved ", packages));
    }
    
    /**
     * To get package by ID from Travel Package Management Module
     */
    @GetMapping("/packages/{id}")
    public ResponseEntity<ApiResponse> getPackageById(@PathVariable("id") Long packageId) {
        TravelPackageDTO packages = service.getPackageById(packageId);
        return ResponseEntity.ok(new ApiResponse(true, "Package retrieved successfully ", packages));
    }
    
}