package com.example.service;

import com.example.client.UserClient;
import com.example.dto.UserDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.TravelPackage;
import com.example.repository.TravelPackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelPackageService {

    private static final Logger logger = LoggerFactory.getLogger(TravelPackageService.class);
    private final TravelPackageRepository repository;
    
    @Autowired
    private final UserClient userClient;

    public TravelPackageService(TravelPackageRepository repository,UserClient userClient) {
        this.repository = repository;
        this.userClient = userClient;
    }

    // Get all packages
    public List<TravelPackage> getAllPackages() {
        logger.info("Fetching all travel packages...");
        return repository.findAll();
    }

    // Get package by ID
    public TravelPackage getPackageById(Long id) {
        logger.debug("Fetching travel package with ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Package not found with ID: {}", id);
                    return new ResourceNotFoundException("Package not found with ID: " + id);
                });
    }
    
    public List<TravelPackage> getPackageByAgentId(Long agentId){
    	logger.info("Fetching packages for agent ID: {} ", agentId);
    	
    	List<TravelPackage> packages = repository.findByAgentId(agentId);
    	
    	if(packages.isEmpty()) {
    		logger.warn("No packages found for agent ID: {}", agentId);
    		throw new ResourceNotFoundException("No packages found for agent ID: "+agentId);
    	}
    	
    	return packages;
    }
    
    

    // Create package
    public TravelPackage createPackage(TravelPackage travelPackage) {
    	
    	Long agentId = travelPackage.getAgentId();
    	logger.info("Validating agent ID: {}", agentId);
    	
    	try {
    		UserDTO user = userClient.getUserById(agentId);
    		logger.info("User fetched from User Service: {}", user);
    		
    		if(user == null) {
    			throw new ResourceNotFoundException("User not found for ID: "+agentId);
    		}
    		
    		if(!"AGENT".equalsIgnoreCase(user.getRole())) {
    			logger.warn("User with ID {} is not an agent. Role: {}", agentId, user.getRole());
    			throw new IllegalArgumentException("User is not authorized to create travel packages.");
    			
    		}
    		System.out.println("Agent fetched: "+ user.getName() + "Role" + user.getRole());
    		
    	}catch(Exception e) {
    			logger.error("Feign call failed for agentId {}: {}", agentId, e.getMessage());
    			throw new ResourceNotFoundException("Agent with ID "+ agentId+" not found or invalid.");
    			
    		}
    		return repository.save(travelPackage);
    	}
    	
    	
    	
    	
    	
//        logger.info("Creating new travel package: {}", travelPackage.getTitle());
//        
//       try {
//       	userClient.getUserById(travelPackage.getAgentId());
//       }catch (Exception e) {
//       	logger.error("Agent ID {} not found in User Management Service", travelPackage.getAgentId());
//       	throw new IllegalArgumentException("Agent with ID"+ travelPackage.getAgentId() + " not found");
//        }
//        return repository.save(travelPackage);
    

    // Update package
    public TravelPackage updatePackage(Long id, TravelPackage updatedPackage) {
        logger.info("Updating travel package with ID: {}", id);
        return repository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedPackage.getTitle());
                    existing.setDescription(updatedPackage.getDescription());
                    existing.setDuration(updatedPackage.getDuration());
                    existing.setPrice(updatedPackage.getPrice());
                    existing.setHighlights(updatedPackage.getHighlights());
                    existing.setFlights(updatedPackage.getFlights());
                    existing.setHotels(updatedPackage.getHotels());
                    existing.setSightseeing(updatedPackage.getSightseeing());
                    existing.setItinerary(updatedPackage.getItinerary());
                    existing.setOffer(updatedPackage.getOffer());
                    logger.info("Package updated successfully with ID: {}", id);
                    return repository.save(existing);
                })
                .orElseThrow(() -> {
                    logger.warn("Package not found for update with ID: {}", id);
                    return new ResourceNotFoundException("Package not found with ID: " + id);
                });
    }

    // Delete package
    public void deletePackage(Long id) {
        logger.info("Attempting to delete travel package with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.error("Package not found with ID: {}", id);
            throw new ResourceNotFoundException("Package not found with ID: " + id);
        }
        repository.deleteById(id);
        logger.info("Package deleted successfully with ID: {}", id);
    }
}

 