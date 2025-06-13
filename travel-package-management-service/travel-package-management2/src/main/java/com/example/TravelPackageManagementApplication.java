package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //Enables Eureka client functionality
public class TravelPackageManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelPackageManagementApplication.class, args);
	}

}
