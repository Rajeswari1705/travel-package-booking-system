package com.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
 
@FeignClient(name = "TravelInsuranceService")
public interface TravelInsuranceClient {
 
	@GetMapping("/api/insurance/price/{userId}")
    double getInsurancePriceByUserId(@PathVariable("userId") Long userId);
	
	@GetMapping("/api/insurance/validate/{insuranceId}")
	boolean validateInsurance(@PathVariable("insuranceId") Long insuranceId);
 
    @PutMapping("/api/insurance/{insuranceId}/booking/{bookingId}")
    String updateInsuranceBookingId(@PathVariable("insuranceId") Long insuranceId, @PathVariable("bookingId") Long bookingId);
}