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
				
				.build(); // finish setting up all routes
						
	}

}
