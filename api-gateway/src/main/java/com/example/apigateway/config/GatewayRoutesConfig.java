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
				
				// Routes for Travel Insurance Service
				.route("TravelInsurance", route -> route
						.path("/api/insurance/**")
			    
						.uri("lb://TravelInsuranceService")) // send to service named "TravelInsuranceService"
				
				// Routes for Travel Assistance Service
				.route("TravelAsssistance", route -> route
						.path("/api/assistance/**")
			    
						.uri("lb://TravelInsuranceService")) // send to service named "TravelInsuranceService"
				

				// Route to reviews-service
				.route("reviews-service", route -> route
					    .path("/api/reviews/**")
					    
					    .uri("lb://RatingsAndReview"))  // send to service named "ratings-and-review-service"
				// Route to review-service
				.route("agent-responses-service", route -> route
					    .path("/api/agent-responses/**")
					    
					    .uri("lb://RatingsAndReview")) // send to service named "ratings-and-review-service"
				// Route to Booking and Payment Module
				.route("booking-service", route -> route
					    .path("/api/bookings/**")
					    
					    .uri("lb://TravelBooking_PaymentModule"))  // send to service named "TravelBooking_PaymentModule"
				// Route to payment end points
				.route("payment-service", route -> route
					    .path("/api/payments/**")
					    
					    .uri("lb://TravelBooking_PaymentModule")) // send to service named "TravelBooking_PaymentModule"

				.build(); // Only one build() at the end

						
	}

}
