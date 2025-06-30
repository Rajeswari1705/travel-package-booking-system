package com.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.booking.dto.UserDTO;

/**
 * Feign client for interacting with the User Management Service.
 * 
 * This client provides methods to retrieve user details by their ID.
 */
@FeignClient(name = "usermanagement-service")
public interface UserClient {

    /**
     * Retrieve customer details by their ID.
     * 
     * @param userId The ID of the customer to be retrieved.
     * @return The UserDTO representing the customer details.
     */
    @GetMapping("/api/users/internal/customer/{id}")
    UserDTO getCustomerById(@PathVariable("id") Long userId);

    // Uncomment the following method if you need to retrieve agent details by their ID.
    // /**
    //  * Retrieve agent details by their ID.
    //  * 
    //  * @param agentId The ID of the agent to be retrieved.
    //  * @return The UserDTO representing the agent details.
    //  */
    // @GetMapping("/api/users/internal/agent/{id}")
    // UserDTO getAgentById(@PathVariable("id") Long agentId); 
}
