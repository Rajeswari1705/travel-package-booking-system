package com.booking.client;

<<<<<<< HEAD

import com.booking.DTO.PackageDTO;
import com.booking.response.ApiResponse;

import java.util.List;

=======
import com.booking.DTO.TravelPackageDTO;
>>>>>>> bb6a3afb12f3bc8e991cc02d0b275e93e06ff9e8
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

/*@FeignClient(name = "travel-package-management")
public interface TravelPackageClient {

<<<<<<< HEAD
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



=======
    @GetMapping("/api/packages/internal/all")
    List<TravelPackageDTO> getAllPackages();
>>>>>>> bb6a3afb12f3bc8e991cc02d0b275e93e06ff9e8

    @GetMapping("/api/packages/{id}")
    TravelPackageDTO getPackageById(@PathVariable("id") Long id);
}