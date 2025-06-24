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

@FeignClient(name = "travel-package-management")
public interface TravelPackageClient {
    @GetMapping("/api/packages/internal/agent-id/{packageId}")
    Map<String, Long> getAgentIdByPackageId(@PathVariable("packageId") Long packageId);
}
