package com.example.travelinsuranceservice.repository;
 
import com.example.travelinsuranceservice.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    List<Insurance> findByUserId(Integer userId);
}
 