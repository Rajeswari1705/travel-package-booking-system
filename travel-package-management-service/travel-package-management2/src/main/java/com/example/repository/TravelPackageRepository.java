package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.TravelPackage;

/**
 * Repository for TravelPackage entity.
 */
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {
	
}
