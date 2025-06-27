package com.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
 
@FeignClient(name = "TravelInsuranceService", url = "http://localhost:8085")
public interface TravelInsuranceClient {
 
	@GetMapping("/api/insurance/price/user/{userId}")
    double getInsurancePriceByUserId(@PathVariable("userId") Long userId);
	
	@GetMapping("/api/insurance/price/insurance/{insuranceId}")
    double getInsurancePriceByInsuranceId(@PathVariable("insuranceId") Integer insuranceId);
	
	@GetMapping("/api/insurance/validate/{insuranceId}")
	boolean validateInsurance(@PathVariable("insuranceId") Integer insuranceId);
 
    @PutMapping("/api/insurance/{insuranceId}/booking/{bookingId}")
    String updateInsuranceBookingId(@PathVariable("insuranceId") Integer insuranceId, @PathVariable("bookingId") Long bookingId);
}