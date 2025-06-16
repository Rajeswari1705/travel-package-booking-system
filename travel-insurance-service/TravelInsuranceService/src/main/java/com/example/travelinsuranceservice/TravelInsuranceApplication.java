package com.example.travelinsuranceservice;

// Import required Spring Boot classes
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

 
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
@EnableFeignClients(basePackages="com.example.travelinsuranceservice.client")
@SpringBootApplication
@EnableDiscoveryClient //Enables Eureka client functionality
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

 