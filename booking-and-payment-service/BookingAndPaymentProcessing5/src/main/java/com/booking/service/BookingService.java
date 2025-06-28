package com.booking.service;

import com.booking.client.TravelPackageClient;
import com.booking.client.TravelInsuranceClient;
import com.booking.client.UserClient;
import com.booking.dto.BookingDTO;
import com.booking.dto.TravelPackageDTO;
import com.booking.dto.UserDTO;
import com.booking.entity.Booking;
import com.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service class for handling booking-related operations.
 * 
 * This class provides methods for creating, retrieving, updating, and deleting bookings.
 */
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private TravelPackageClient travelPackageClient;

    @Autowired
    private TravelInsuranceClient travelInsuranceClient;

    @Autowired
    private UserClient userClient;

    private static final Logger logger = Logger.getLogger(BookingService.class.getName());

    /**
     * Create a new booking.
     * 
     * @param bookingRequest The booking entity to be created.
     * @return BookingDTO containing the details of the created booking.
     */
    public BookingDTO createBooking(Booking bookingRequest) {
        Long userId = bookingRequest.getUserId();
        Long packageId = bookingRequest.getPackageId();
        Integer insuranceId = bookingRequest.getInsuranceId();

        logger.info("Creating booking for userId: " + userId + " and packageId: " + packageId);

        // Validate User
        UserDTO user = userClient.getCustomerById(userId);
        if (user == null || !"CUSTOMER".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("User is not a valid CUSTOMER.");
        }

        // Validate Package
        TravelPackageDTO travelPackage = travelPackageClient.getPackageById(packageId);
        if (travelPackage == null) {
            throw new IllegalArgumentException("Invalid travel package ID.");
        }

        // Optional: Validate Insurance if selected
        if (insuranceId != null && insuranceId > 0) {
            boolean exists = travelInsuranceClient.validateInsurance(insuranceId);
            if (!exists) {
                throw new IllegalArgumentException("Selected Insurance ID is invalid.");
            }
        }

        // Create and save booking
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setPackageId(packageId);
        booking.setInsuranceId(insuranceId);
        booking.setTripStartDate(travelPackage.getTripStartDate());
        booking.setTripEndDate(travelPackage.getTripEndDate());
        booking.setStatus("PENDING");
        Booking savedBooking = bookingRepo.save(booking);

        // Build response DTO
        BookingDTO dto = new BookingDTO();
        dto.setBookingId(savedBooking.getBookingId());
        dto.setUserId(userId);
        dto.setPackageId(packageId);
        dto.setInsuranceId(insuranceId);
        dto.setTripStartDate(savedBooking.getTripStartDate());
        dto.setTripEndDate(savedBooking.getTripEndDate());
        dto.setStatus(savedBooking.getStatus());
        dto.setPaymentId(savedBooking.getPaymentId());

        logger.info("Booking created successfully with bookingId: " + savedBooking.getBookingId());
        return dto;
    }

    /**
     * Retrieve all bookings.
     * 
     * @return A list of all bookings.
     */
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    /**
     * Retrieve a booking by its ID.
     * 
     * @param id The ID of the booking to be retrieved.
     * @return The booking entity.
     */
    public Booking getBookingById(Long id) {
        return bookingRepo.findById(id).orElse(null);
    }

    /**
     * Retrieve a booking by its ID for internal use.
     * 
     * @param id The ID of the booking to be retrieved.
     * @return The booking entity.
     */
    public Booking getInternalBookingById(Long id) {
        return bookingRepo.findById(id).orElse(null);
    }

    /**
     * Delete a booking by its ID.
     * 
     * @param id The ID of the booking to be deleted.
     */
    public void deleteBooking(Long id) {
        bookingRepo.deleteById(id);
    }

    /**
     * Cancel a booking by its ID.
     * 
     * @param bookingId The ID of the booking to be canceled.
     * @return ResponseEntity containing the cancellation status and HTTP status.
     */
    public ResponseEntity<String> cancelBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElse(null);
        if (booking == null) {
            return ResponseEntity.badRequest().body("Booking not found.");
        }
        LocalDate today = LocalDate.now();
        if (booking.getTripStartDate().minusDays(7).isBefore(today)) {
            return ResponseEntity.badRequest().body("Cancellation not allowed. Must cancel at least 7 days before departure.");
        }
        booking.setStatus("CANCELLED");
        bookingRepo.save(booking);
        return ResponseEntity.ok("Booking cancelled successfully.");
    }

    /**
     * Retrieve bookings by user ID.
     * 
     * @param userId The ID of the user whose bookings are to be retrieved.
     * @return A list of bookings associated with the user ID.
     */
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepo.findByUserId(userId);
    }

    /**
     * Check if a user has completed a package.
     * 
     * @param userId The ID of the user.
     * @param packageId The ID of the package.
     * @return boolean indicating whether the user has completed the package.
     */
    public boolean hasUserCompletedPackage(Long userId, String packageId) {
        List<Booking> bookings = bookingRepo.findByUserId(userId);
        LocalDate today = LocalDate.now();
        return bookings.stream()
            .anyMatch(b ->
                String.valueOf(b.getPackageId()).equals(packageId) &&
                "CONFIRMED".equalsIgnoreCase(b.getStatus()) &&
                !b.getTripEndDate().isAfter(today) // includes today
            );
    }

    /**
     * Retrieve all travel packages.
     * 
     * @return A list of TravelPackageDTO representing all travel packages.
     */
    public List<TravelPackageDTO> getAllPackages() {
        return travelPackageClient.getAllPackages();
    }

    /**
     * Retrieve a travel package by its ID.
     * 
     * @param packageId The ID of the travel package to be retrieved.
     * @return The TravelPackageDTO representing the travel package.
     */
    public TravelPackageDTO getPackageById(Long packageId) {
        return travelPackageClient.getPackageById(packageId);
    }
}
