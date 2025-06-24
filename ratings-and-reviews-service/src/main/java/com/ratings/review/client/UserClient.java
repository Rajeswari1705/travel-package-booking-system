package com.ratings.review.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ratings.review.dto.UserDTO;

/**
 * Feign client interface for interacting with the User Management service.
 * This client is used to retrieve customer user details by their ID.
 */
@FeignClient(name = "usermanagement-service") // Connects to the user management service registered with Eureka
public interface UserClient {

    /**
     * Retrieves customer user details by user ID.
     *
     * @param id the ID of the customer user
     * @return a UserDTO containing user information
     */
    @GetMapping("/api/users/internal/customer/{id}") // Maps to the internal endpoint for fetching customer user data
    UserDTO getUserById(@PathVariable("id") Long id);
}
