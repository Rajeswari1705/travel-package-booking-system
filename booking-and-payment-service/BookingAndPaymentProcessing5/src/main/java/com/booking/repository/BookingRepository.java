package com.booking.repository;
 
import com.booking.entity.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
 
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUserId(Long userId);
	int countByUserId(Long userId);
	List<Booking> findByPackageId(Long packageId);
}
 

<<<<<<< HEAD
List<Booking> findByUserId(Long userId);
int countByUserId(Long userId);
List<Booking> findByPackageId(Long packageId);

}
=======
>>>>>>> 78eeeaf1cad94f5fa8ca8eae933a628c316725f9
