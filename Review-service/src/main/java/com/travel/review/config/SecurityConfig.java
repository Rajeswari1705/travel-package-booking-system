package com.travel.review.config;

import com.travel.review.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Enable Spring Security's web security support
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Inject JwtAuthenticationFilter (assuming it's a @Component or @Bean)
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable CSRF for REST APIs
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless sessions for REST
            .and()
            .authorizeHttpRequests(authorize -> authorize
                // Public endpoints for viewing reviews
                .requestMatchers("/api/reviews/package/**").permitAll()
                .requestMatchers("/api/reviews/user/**").permitAll()
                // Endpoints requiring authentication
                .requestMatchers("/api/reviews").authenticated() // For adding reviews (customers)
                .requestMatchers("/api/reviews/{id}").authenticated() // For updating/deleting reviews (customers/agents)
                // New endpoint for travel agent responses
                .requestMatchers("/api/reviews/{reviewId}/respond").hasRole("AGENT") // Only agents can respond
                .anyRequest().authenticated() // All other requests require authentication
            )
            // Add JWT filter before the Spring Security's UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}