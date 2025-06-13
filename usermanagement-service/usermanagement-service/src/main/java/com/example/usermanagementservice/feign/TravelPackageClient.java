package com.example.usermanagementservice.feign;
import com.example.usermanagementservice.dto.PackageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//Feign client to call travel-package-management-service
@FeignClient(name = "travel-package-management")
public interface TravelPackageClient {
	//Endpoint expected to exist in the travel package service
	
	@GetMapping("")
	List<PackageDTO> getPackagesByAgentId(@PathVariable("agentId") Long agentId);
}
