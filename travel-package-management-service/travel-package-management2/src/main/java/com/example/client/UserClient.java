package com.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="usermanagement-service")
public interface UserClient {
	
	@GetMapping("/api/users/{id}")
	String getUserById(@PathVariable("id") Long id);

}
