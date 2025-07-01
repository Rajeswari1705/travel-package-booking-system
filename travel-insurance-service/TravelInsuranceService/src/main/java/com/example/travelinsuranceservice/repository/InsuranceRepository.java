package com.example.travelinsuranceservice.repository;
 
import com.example.travelinsuranceservice.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
 
import java.util.List;
import java.util.Optional;
 
/**
 * Repository interface for Insurance entity.
 * Extends JpaRepository to get CRUD operations automatically.
 */
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
 
    /**
     * Custom finder method to retrieve all insurance records for a given userId.
     * Spring Data JPA automatically generates the query from the method name.
     *
     * @param userId ID of the user
     * @return List of Insurance objects associated with the user
     */
    List<Insurance> findByUserId(Long userId);
    
    List<Insurance> findByUserIdAndBookingIdIsNull(Long userId);

	boolean existsByInsuranceId(Integer insuranceId);

	Optional<Insurance> findById(Integer insuranceId);
	
	Optional<Insurance> findByBookingId(Long bookingId);
}
 