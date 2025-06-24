package com.example.travelinsuranceservice.client;
 
import com.example.travelinsuranceservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
/**
 * Feign client to communicate with the User module.
 */
@FeignClient(name = "usermanagement-service")
public interface UserClient {
 
    /**
     * Fetch user details by userId from the User module.
     * @param long1 the ID of the user
     * @return the UserDTO containing user information
     */
    @GetMapping("/api/users/internal/customer/{id}")
    UserDTO getUserById(@PathVariable("id") Long userId);
}

 