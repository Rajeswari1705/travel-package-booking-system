package com.example.controller;

import com.example.model.TravelPackage;
import com.example.response.ApiResponse;
import com.example.service.TravelPackageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dto.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class TravelPackageController {

    private final TravelPackageService service;

    public TravelPackageController(TravelPackageService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<ApiResponse> getAllPackages() {
        List<TravelPackage> packages = service.getAllPackages();
        return ResponseEntity.ok(new ApiResponse(true, "All packages retrieved ", packages));
    }
    //for booking payment module
    @GetMapping("/search/title/{title}")
    public ResponseEntity<ApiResponse> getByTitlePath(@PathVariable String title) {
        List<TravelPackage> packages = service.searchByTitle(title);
        return ResponseEntity.ok(new ApiResponse(true, "Packages found by title", packages));
    }

    @GetMapping("/search/price/{maxPrice}")
    public ResponseEntity<ApiResponse> getByPricePath(@PathVariable double maxPrice) {
        List<TravelPackage> packages = service.searchByPrice(maxPrice);
        return ResponseEntity.ok(new ApiResponse(true, "Packages under price", packages));
    }

    @GetMapping("/search/offer/{couponCode}")
    public ResponseEntity<ApiResponse> getByOfferPath(@PathVariable String couponCode) {
        List<TravelPackage> packages = service.searchByOffer(couponCode);
        return ResponseEntity.ok(new ApiResponse(true, "Packages with offer", packages));
    }


    

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        TravelPackage travelPackage = service.getPackageById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Package found", travelPackage));
    }
    @GetMapping("/agent/{agentId}")
    public ResponseEntity<ApiResponse> getByAgentId(@PathVariable Long agentId){
    	List<TravelPackage> packages = service.getPackageByAgentId(agentId);
    	return ResponseEntity.ok(new ApiResponse(true, "Package by agent retrieve", packages));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody TravelPackage travelPackage) {
        TravelPackage created = service.createPackage(travelPackage);
        return ResponseEntity.ok( new ApiResponse(true, "Package created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @Valid @RequestBody TravelPackage travelPackage) {
        TravelPackage updated = service.updatePackage(id, travelPackage);
        return ResponseEntity.ok(new ApiResponse(true, "Package updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        service.deletePackage(id);
        return ResponseEntity.ok(new ApiResponse(true, "Package deleted successfully", null));
    }
    
    //for DTO implementation
    
    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getPackageWithAllDetails(@PathVariable Long id){
    	TravelPackage pkg = service.getPackageById(id);
    	TravelPackageDTO dto = service.convertToDTO(pkg);
    	return ResponseEntity.ok(new ApiResponse(true, "Package with full details", dto));
    }
}
