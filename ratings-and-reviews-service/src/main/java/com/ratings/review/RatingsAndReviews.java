package com.ratings.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient // This enables Eureka client functionality
public class RatingsAndReviews {

public static void main(String[] args) {
SpringApplication.run(RatingsAndReviews.class, args);
}
}
