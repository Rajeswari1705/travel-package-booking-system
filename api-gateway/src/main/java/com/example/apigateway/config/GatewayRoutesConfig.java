package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {
	
	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				// Route to user-service
				.route("usermanagement-service", route -> route
					    .path("/api/users/**")
					    
					    .uri("lb://usermanagement-service")) // send to service named "usermanagement-service"
				
		
				// Routes for Travel Package Management Service
				.route("travel-package-management", route -> route
						.path("/api/packages/**")
			    
						.uri("lb://travel-package-management")) // send to service named "travel-package-management"

				// Route to reviews-service
				.route("reviews-service", route -> route
					    .path("/api/reviews/**")
					    
					    .uri("lb://ratings-and-reviews-service"))  // send to service named "ratings-and-review-service"
				// Route to user-service
				.route("agent-responses-service", route -> route
					    .path("agent-responses-service")
					    
					    .uri("lb://ratings-and-reviews-service")) // send to service named "ratings-and-review-service"
				
				// Routes for Travel Insurance Service
				.route("TravelInsurance", route -> route
						.path("/api/insurance/**")
			    
						.uri("lb://TravelInsuranceService")) // send to service named "TravelInsuranceService"
				
				// Routes for Travel Insurance Service
				.route("TravelAsssistance", route -> route
						.path("/api/assistance/**")
			    
						.uri("lb://TravelInsuranceService")) // send to service named "TravelInsuranceService"
				

				.build(); // Only one build() at the end

						
	}

}
