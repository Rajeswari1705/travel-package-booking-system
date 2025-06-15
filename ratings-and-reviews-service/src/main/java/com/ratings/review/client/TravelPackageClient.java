package com.ratings.review.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ratings.review.dto.TravelPackageDTO;

@FeignClient(name = "travel-package-management")
public interface TravelPackageClient {

    @GetMapping("/api/packages/{id}")
    TravelPackageDTO getPackageById(@PathVariable("id") Long id);
}
