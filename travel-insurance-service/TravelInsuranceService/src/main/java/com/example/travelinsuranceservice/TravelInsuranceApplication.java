package com.example.travelinsuranceservice;
 
// Import required Spring Boot classes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
/**
 * Main entry point for the Spring Boot Travel Insurance & Assistance module.
 *
 * What this file does:
 * 🔹 Marks the starting point of the application
 * 🔹 Enables auto-configuration, component scanning, and bean registration
 * 🔹 Boots up the Spring application context and starts the embedded server (Tomcat)
 *
 * Annotations used:
 * @SpringBootApplication is a combination of:
 *   - @Configuration: marks this class as a source of bean definitions
 *   - @EnableAutoConfiguration: tells Spring Boot to auto-configure beans based on dependencies
 *   - @ComponentScan: scans this package and all sub-packages for Spring components
 */
@SpringBootApplication
public class TravelInsuranceApplication {
 
    /**
     * Main method that gets executed when the application starts.
     *
     * @param args command-line arguments (optional)
     */
    public static void main(String[] args) {
        // Launch the Spring Boot application
        SpringApplication.run(TravelInsuranceApplication.class, args);
    }
}

 