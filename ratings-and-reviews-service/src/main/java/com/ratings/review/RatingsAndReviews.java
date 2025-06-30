package com.ratings.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Ratings and Reviews microservice.
 * This service is responsible for handling user ratings and reviews.
 * It is a Spring Boot application that registers itself with Eureka for service discovery
 * and uses Feign clients to communicate with other microservices.
 */
@EnableFeignClients 
@SpringBootApplication // Marks this class as a Spring Boot application
@EnableDiscoveryClient // Enables Eureka client functionality for service registration
public class RatingsAndReviews {

    // SLF4J Logger for logging application events
    private static final Logger logger = LoggerFactory.getLogger(RatingsAndReviews.class);

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command-line arguments passed during application startup
     */
    public static void main(String[] args) {
        logger.info("Starting Ratings and Reviews microservice...");
        SpringApplication.run(RatingsAndReviews.class, args);
        logger.info("Ratings and Reviews microservice started successfully.");
    }
}
