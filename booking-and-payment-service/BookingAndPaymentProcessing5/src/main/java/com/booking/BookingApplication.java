package com.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for starting the Booking and Payment Processing Spring Boot application.
 * 
 * This service is registered with Eureka and uses Feign clients for communication 
 * with external services like Travel Package, User Management, and Insurance modules.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.booking.client")
public class BookingApplication {

    /**
     * Main method to bootstrap the Spring Boot application.
     * 
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }
}

