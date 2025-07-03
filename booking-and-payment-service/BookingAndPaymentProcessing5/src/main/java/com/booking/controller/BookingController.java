package com.booking.controller;

import com.booking.dto.BookingDTO;
import jakarta.validation.Valid;
import com.booking.entity.Booking;
import com.booking.service.BookingService;
import com.booking.response.ApiResponse;
import com.booking.dto.TravelPackageDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Controller for handling booking-related requests.
 * 
 * This controller provides endpoints for creating, retrieving, updating, and deleting bookings.
 */
//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;
    private static final Logger logger = Logger.getLogger(BookingController.class.getName());

    /**
     * Constructor for BookingController.
     * 
     * @param service The BookingService instance to be used by this controller.
     */
    public BookingController(BookingService service) {
        this.service = service;
    }

    /**
     * Create a new booking.
     * 
     * @param booking The booking entity to be created.
     * @return ResponseEntity containing the created BookingDTO and HTTP status.
     */
    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody Booking booking) {
        try {
            BookingDTO bookingDTO = service.createBooking(booking);
            return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            ex.printStackTrace(); // shows full error in backend logs
            logger.severe("Error creating booking: " + ex.getMessage());

            // return a proper error JSON message to frontend
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ApiResponse(false, "Booking failed: " + ex.getMessage(), null));
        }
    }
    /**
     * Retrieve all bookings.
     * 
     * @return ResponseEntity containing the list of all bookings and HTTP status.
     */
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = service.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    /**
     * Retrieve a booking by its ID.
     * 
     * @param id The ID of the booking to be retrieved.
     * @return ResponseEntity containing the booking and HTTP status.
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
     * Cancel a booking by its ID.
     * 
     * @param id The ID of the booking to be canceled.
     * @return ResponseEntity containing the cancellation status and HTTP status.
     */
    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        return service.cancelBooking(id);
    }

    /**
     * Delete a booking by its ID.
     * 
     * @param id The ID of the booking to be deleted.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteBooking(id);
    }

    /**
     * Retrieve bookings by user ID.
     * 
     * @param userId The ID of the user whose bookings are to be retrieved.
     * @return ResponseEntity containing the list of bookings and HTTP status.
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
     * Check if a user has completed a package.
     * 
     * @param userId The ID of the user.
     * @param packageId The ID of the package.
     * @return boolean indicating whether the user has completed the package.
     */
    @GetMapping("/user/{userId}/package/{packageId}/completed")
    public boolean hasUserCompletedPackage(@PathVariable Long userId, @PathVariable Long packageId) {
        return service.hasUserCompletedPackage(userId, packageId);
    }

    /**
     * Retrieve a booking by its ID for internal use.
     * 
     * @param id The ID of the booking to be retrieved.
     * @return ResponseEntity containing the booking and HTTP status.
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
     * Retrieve all travel packages.
     * 
     * @return ResponseEntity containing the ApiResponse with the list of packages and HTTP status.
     */
    @GetMapping("/packages")
    public ResponseEntity<ApiResponse> getAllPackages() {
        List<TravelPackageDTO> packages = service.getAllPackages();
        return ResponseEntity.ok(new ApiResponse(true, "All packages retrieved", packages));
    }

    /**
     * Retrieve a travel package by its ID.
     * 
     * @param packageId The ID of the package to be retrieved.
     * @return ResponseEntity containing the ApiResponse with the package details and HTTP status.
     */
    @GetMapping("/packages/{id}")
    public ResponseEntity<ApiResponse> getPackageById(@PathVariable("id") Long packageId) {
        TravelPackageDTO packages = service.getPackageById(packageId);
        return ResponseEntity.ok(new ApiResponse(true, "Package retrieved successfully", packages));
    }
}
