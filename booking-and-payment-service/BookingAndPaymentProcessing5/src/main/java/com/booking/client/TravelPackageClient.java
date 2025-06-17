package com.booking.client;

import com.booking.DTO.TravelPackageDTO;
import com.booking.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "travel-package-management")
public interface TravelPackageClient {

    @GetMapping("/api/packages/{id}")
    TravelPackageDTO getPackageById(@PathVariable("id") Long id);

    @GetMapping("/api/packages/search/by-title")
    ApiResponse searchByTitle(@RequestParam("title") String title);

    @GetMapping("/api/packages/search/by-price")
    ApiResponse searchByPrice(@RequestParam("maxPrice") double maxPrice);
}
