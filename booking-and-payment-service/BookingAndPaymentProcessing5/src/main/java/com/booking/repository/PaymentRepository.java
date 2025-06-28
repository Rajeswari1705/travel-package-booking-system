package com.booking.repository;

import com.booking.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Payment entities.
 * This interface provides methods for performing CRUD operations on Payment entities.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Find a payment by booking ID and user ID.
     * 
     * @param bookingId The ID of the booking.
     * @param userId The ID of the user.
     * @return An Optional containing the payment associated with the booking ID and user ID.
     */
    Optional<Payment> findByBookingIdAndUserId(Long bookingId, Long userId);
}
