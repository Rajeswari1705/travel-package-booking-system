package com.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.booking.dto.TravelPackageDTO;

import java.util.List;

/**
 * Feign client for interacting with the Travel Package Management Service.
 * 
 * This client provides methods to retrieve all travel packages and to get a specific package by its ID.
 */
@FeignClient(name = "travel-package-management")
public interface TravelPackageClient {

    /**
     * Retrieve all travel packages.
     * 
     * @return A list of TravelPackageDTO representing all travel packages.
     */
    @GetMapping("/api/packages/internal/all")
    List<TravelPackageDTO> getAllPackages();

    /**
     * Retrieve a travel package by its ID.
     * 
     * @param id The ID of the travel package to be retrieved.
     * @return The TravelPackageDTO representing the travel package.
     */
    @GetMapping("/api/packages/internal/{id}")
    TravelPackageDTO getPackageById(@PathVariable("id") Long id);
}
