package com.booking.client;

import com.booking.DTO.TravelPackageDTO;
import com.booking.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "travel-package-management") // This name must match the spring.application.name
public interface TravelPackageClient {

	@GetMapping("/api/packages/{id}")
	ApiResponse getPackageById(@PathVariable("id") Long id);

    @GetMapping("/api/packages/search/title/{title}")
    ApiResponse searchByTitle(@PathVariable("title") String title);

    @GetMapping("/api/packages/search/price/{maxPrice}")
    ApiResponse searchByPrice(@PathVariable("maxPrice") double maxPrice);

    @GetMapping("/api/packages/search/offer/{couponCode}")
    ApiResponse searchByOffer(@PathVariable("couponCode") String couponCode);
}


