package com.booking.controller;

import com.booking.DTO.BookingDTO;
import com.booking.entity.Booking;
import com.booking.service.BookingService;
import com.booking.DTO.TravelPackageDTO;
//import com.example.model.TravelPackage;
import com.booking.response.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    private static final Logger logger = Logger.getLogger(BookingController.class.getName());

    @PostMapping("/user/{userId}/package/{packageId}")
    public ResponseEntity<BookingDTO> createBooking(@PathVariable Long userId, @PathVariable Long packageId) {
        try {
            BookingDTO bookingDTO = bookingService.createBooking(userId, packageId); // Define bookingDTO here
            return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            logger.severe("Error creating booking: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
    
//    @GetMapping("/packages/{id}")
//    public ResponseEntity<?> getAllPackagesOfAgent(@PathVariable Long id) {
//        UserService userService = new UserService(); // Create a local variable userService
//        List<TravelPackageDTO> packages = userService.fetchAllPackagesByAgent(id); 
//        return ResponseEntity.ok(packages);
//    }

    
//    @GetMapping("/packages/{id}")
//    public ResponseEntity<?> getAllPackagesOfAgent(@PathVariable Long id) {
//        List<TravelPackageDTO> packages = userService.fetchAllPackagesByAgent(id); 
//        return ResponseEntity.ok(packages);
//    }
    
    
    @GetMapping(produces = "application/json")
    public ResponseEntity<ApiResponse> getAllPackages() {
        List<TravelPackage> packages = service.getAllPackages();
        return ResponseEntity.ok(new ApiResponse(true, "All packages retrieved ", packages));
    }

    
    

//    @GetMapping(value = "/{id}", produces = "application/json")
//    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
//        TravelPackage travelPackage = service.getPackageById(id);
//        return ResponseEntity.ok(new ApiResponse(true, "Package found", travelPackage));
//    }

  
}
