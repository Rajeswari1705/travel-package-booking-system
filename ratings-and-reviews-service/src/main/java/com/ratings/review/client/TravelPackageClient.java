package com.ratings.review.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ratings.review.dto.TravelPackageDTO;

/**
 * Feign client interface for interacting with the Travel Package Management service.
 * This client is used to retrieve travel package details and validate agent ownership.
 */
//@FeignClient(name = "travel-package-management") // Connects to the travel package service using a configurable URL
//public interface TravelPackageClient {
//
//    /**
//     * Retrieves the details of a travel package by its ID.
//     *
//     * @param id the ID of the travel package
//     * @return a TravelPackageDTO containing package details
//     */
////    @GetMapping("/api/packages/{id}") // Maps to the endpoint that returns package details
////    TravelPackageDTO getPackageById(@PathVariable("id") Long id);
//
//    /**
//     * Retrieves the agent ID associated with a specific travel package.
//     * This method is used for validating whether a given agent owns the package.
//     *
//     * @param id the ID of the travel package
//     * @return the agent ID who owns the package
//     */
//    @GetMapping("/api/packages/{packageId}/agent") // Maps to the endpoint that returns the agent ID for a package
//    Long getAgentIdByPackage(@PathVariable("packageId") Long packageId);
//}

@FeignClient(name = "travel-package-management")
public interface TravelPackageClient {
    @GetMapping("/api/packages/{packageId}/agent")
    Map<String, Long> getAgentIdByPackageId(@PathVariable("packageId") Long packageId);
}
