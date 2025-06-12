package com.example.travelinsuranceservice.controller;
 
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {
 
    @Autowired
    private InsuranceService service;
 
    private static final Logger logger = LoggerFactory.getLogger(InsuranceController.class);
 
    @PostMapping
    public ResponseEntity<Insurance> addInsurance(@RequestBody Insurance insurance) {
        logger.info("Creating new insurance for userId: {}", insurance.getUserId());
        return new ResponseEntity<>(service.createInsurance(insurance), HttpStatus.CREATED);
    }
 
    @GetMapping("/{userId}")
    public List<Insurance> getByUser(@PathVariable Integer userId) {
        logger.info("Fetching insurance for userId: {}", userId);
        return service.getUserInsurance(userId);
    }
 
    @PutMapping("/{insuranceId}/status")
    public ResponseEntity<Insurance> updateStatus(@PathVariable Integer insuranceId, @RequestParam String status) {
        logger.info("Updating insurance status for ID {} to {}", insuranceId, status);
        Insurance updated = service.updateInsuranceStatus(insuranceId, status);
        if (updated != null)
            return new ResponseEntity<>(updated, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

 