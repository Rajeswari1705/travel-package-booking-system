package com.travel.review.repository;

import com.travel.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPackageId(Long packageId);
    List<Review> findByUserId(Long userId);
}