package com.booking.client;


import com.booking.DTO.PackageDTO;
import com.booking.response.ApiResponse;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/*@FeignClient(name = "travel-package-management")
public interface TravelPackageClient {

    @GetMapping("/{id}")
    ApiResponse getPackageById(@PathVariable("id") Long id);

    @GetMapping("/search/title/{title}")
    ApiResponse searchByTitle(@PathVariable("title") String title);

    @GetMapping("/search/price/{maxPrice}")
    ApiResponse searchByPrice(@PathVariable("maxPrice") double maxPrice);

    @GetMapping("/search/offer/{couponCode}")
    ApiResponse searchByOffer(@PathVariable("couponCode") String couponCode);
}*/

@FeignClient(name = "travel-package-management")
public interface TravelPackageClient {
    @GetMapping("api/packages/getallpackages")
    List<PackageDTO> getAllPackages();
}




