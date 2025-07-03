package com.booking.service;
import com.booking.repository.PaymentRepository; 
import java.util.Optional; 
import com.booking.entity.Payment;
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
	private PaymentRepository paymentRepo;

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

        // 1. Validate User
        UserDTO user;
        try {
            user = userClient.getCustomerById(userId);
            if (user == null || !"CUSTOMER".equalsIgnoreCase(user.getRole())) {
                throw new RuntimeException("User is not a valid CUSTOMER.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch user details: " + ex.getMessage());
        }

        // 2. Validate Package
        TravelPackageDTO travelPackage;
        try {
            travelPackage = travelPackageClient.getPackageById(packageId);
            if (travelPackage == null) {
                throw new IllegalArgumentException("Invalid travel package ID.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch package details: " + ex.getMessage());
        }

        // 3. Validate Insurance (optional)
        if (insuranceId != null && insuranceId > 0) {
            try {
                boolean exists = travelInsuranceClient.validateInsurance(insuranceId);
                if (!exists) {
                    throw new IllegalArgumentException("Selected Insurance ID is invalid.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException("Failed to validate insurance: " + ex.getMessage());
            }
        }

        // 4. Create and save booking
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setPackageId(packageId);
        booking.setInsuranceId(insuranceId);
        booking.setTripStartDate(travelPackage.getTripStartDate());
        booking.setTripEndDate(travelPackage.getTripEndDate());
        booking.setStatus("PENDING");

        Booking savedBooking = bookingRepo.save(booking);

        // 5. Build response DTO
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
    
// For reviews and ratings module
    public boolean hasUserCompletedPackage(Long userId, Long packageId) { 
        // Changed to use string concatenation for java.util.logging.Logger
        logger.info("Checking review eligibility (hasUserCompletedPackage) for userId: " + userId + " and packageId: " + packageId);

        // Fetch ALL bookings for the user first
        List<Booking> allUserBookings = bookingRepo.findByUserId(userId);

        if (allUserBookings.isEmpty()) {
            // Changed to use string concatenation for java.util.logging.Logger
            logger.info("No bookings found for userId: " + userId);
            return false;
        }

        LocalDate today = LocalDate.now();

        // Now, filter this list in memory by packageId and then apply other checks
        for (Booking booking : allUserBookings) {
            if (booking.getPackageId().equals(packageId) && // <--- Manual filtering here
                "CONFIRMED".equalsIgnoreCase(booking.getStatus()) &&
                !booking.getTripEndDate().isAfter(today)) { // Check if trip end date is past or today

                if (booking.getPaymentId() != null) {
                    Optional<Payment> paymentOpt = paymentRepo.findById(booking.getPaymentId());

                    if (paymentOpt.isPresent()) {
                        Payment payment = paymentOpt.get();
                        if ("PAID".equalsIgnoreCase(payment.getStatus()) || "COMPLETED".equalsIgnoreCase(payment.getStatus())) {
                            // Changed to use string concatenation for java.util.logging.Logger
                            logger.info("User " + userId + " is eligible for review for package " + packageId +
                                        ". Booking ID: " + booking.getBookingId() + ", Payment ID: " + payment.getPaymentId());
                            return true; // Found an eligible booking, so return true immediately
                        }
                    } else {
                        // Changed to use string concatenation for java.util.logging.Logger and 'warning' method
                        logger.warning("Booking " + booking.getBookingId() + " is CONFIRMED but no payment found for paymentId " + booking.getPaymentId());
                    }
                } else {
                    // Changed to use string concatenation for java.util.logging.Logger and 'warning' method
                    logger.warning("Booking " + booking.getBookingId() + " is CONFIRMED but has no paymentId. Payment status check skipped.");
                }
            }
        }
        // Changed to use string concatenation for java.util.logging.Logger
        logger.info("User " + userId + " is not eligible for review for package " + packageId + ". No booking found meeting all criteria (confirmed, paid, journey completed).");
        return false;
    }
}
