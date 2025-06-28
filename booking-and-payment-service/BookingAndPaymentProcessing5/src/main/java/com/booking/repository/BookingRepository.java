package com.booking.repository;

import com.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for Booking entities.
 * This interface provides methods for performing CRUD operations on Booking entities.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Find bookings by user ID.
     * 
     * @param userId The ID of the user.
     * @return A list of bookings associated with the user ID.
     */
    List<Booking> findByUserId(Long userId);

    /**
     * Count the number of bookings by user ID.
     * 
     * @param userId The ID of the user.
     * @return The number of bookings associated with the user ID.
     */
    int countByUserId(Long userId);

    /**
     * Find bookings by package ID.
     * 
     * @param packageId The ID of the travel package.
     * @return A list of bookings associated with the package ID.
     */
    List<Booking> findByPackageId(Long packageId);
}
