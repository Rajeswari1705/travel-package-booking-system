package com.example.travelinsuranceservice.client;
 
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
/**
 * Feign client to communicate with the User Management service.
 * Uses Eureka service discovery with name 'user-service'.
 */
@FeignClient(name = "usermanagement-service")  // This should match the application name of the user service
public interface UserClient {
 
    /**
     * Calls the User service to verify if the provided user ID exists.
     *
     * @param userId the ID of the user to validate
     * @return true if the user exists, false otherwise
     */
    @GetMapping("/api/users/validate/{userId}")
    boolean isUserValid(@PathVariable("userId") Integer userId);
}

 
