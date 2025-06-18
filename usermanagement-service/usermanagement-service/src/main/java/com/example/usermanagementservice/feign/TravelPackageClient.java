package com.example.usermanagementservice.feign;

import com.example.usermanagementservice.dto.PackageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "travel-package-management")
public interface TravelPackageClient {
    @GetMapping("/api/packages/agent/{agentId}")
    List<PackageDTO> getPackagesByAgentId(@PathVariable("agentId") Long agentId);
}