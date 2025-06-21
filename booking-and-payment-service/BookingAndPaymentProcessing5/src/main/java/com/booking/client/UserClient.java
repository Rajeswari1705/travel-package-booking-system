package com.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.booking.DTO.UserDTO;

@FeignClient(name = "usermanagement-service")
public interface UserClient {
    @GetMapping("api/users/internal/customer/{id}")
    UserDTO getUserById(@PathVariable("id") Long userId);
}