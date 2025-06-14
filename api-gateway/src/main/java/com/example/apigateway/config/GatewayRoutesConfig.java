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

				
				
				// Route to reviews-service (Aligned with ReviewController)
                .route("reviews-service", route -> route
                        .path("/api/reviews/**")
                        .uri("lb://ratings-and-reviews-service")) // Corrected service name to match ReviewController

                // Route to agent-responses-service (Aligned with AgentResponseController)
                .route("agent-responses-service", route -> route
                        .path("/api/agent-responses/**") // Added `/**` for broader endpoint handling
                        .uri("lb://ratings-and-reviews-service")) // Ensured service name matches ReviewController
				
				// Routes for Travel Insurance Service
				.route("TravelInsurance", route -> route
						.path("/api/insurance/**")
			    
						.uri("lb://TravelInsuranceService")) // send to service named "TravelInsuranceService"
				
				// Routes for Travel Assistance Service
				.route("TravelAsssistance", route -> route
						.path("/api/assistance/**")
			    
						.uri("lb://TravelInsuranceService")) // send to service named "TravelInsuranceService"
				

				.build(); // Only one build() at the end

						
	}

}
