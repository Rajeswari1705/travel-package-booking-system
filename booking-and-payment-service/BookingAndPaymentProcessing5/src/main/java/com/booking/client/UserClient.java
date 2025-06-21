package com.booking.client;

<<<<<<< HEAD
=======
import com.booking.DTO.UserDTO;
>>>>>>> bb6a3afb12f3bc8e991cc02d0b275e93e06ff9e8
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

<<<<<<< HEAD
import com.booking.DTO.UserDTO;

@FeignClient(name = "usermanagement-service")
public interface UserClient {
    @GetMapping("api/users/internal/customer/{id}")
=======
@FeignClient(name = "usermanagement-service")
public interface UserClient {

    @GetMapping("/api/users/internal/{id}")
>>>>>>> bb6a3afb12f3bc8e991cc02d0b275e93e06ff9e8
    UserDTO getUserById(@PathVariable("id") Long userId);
}