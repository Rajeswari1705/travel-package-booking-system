package com.booking.repository;

import com.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

List<Booking> findByUserId(Long userId);
int countByUserId(Long userId);
List<Booking> findByPackageId(Long packageId);
<<<<<<< HEAD

=======
	
>>>>>>> da78b8b44b03d258736ef3becfb5e869e44cb504
}