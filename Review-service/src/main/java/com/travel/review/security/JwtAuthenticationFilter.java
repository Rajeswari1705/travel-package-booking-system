package com.travel.review.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Assume you have a JwtUtil for token operations and a UserDetailsService for loading user details
// These would be separate classes in a real application.
class JwtUtil {
    public String extractUsername(String token) {
        // Dummy implementation: In a real app, parse JWT to get username
        return "testuser"; // Or extract from token
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        // Dummy implementation: In a real app, validate signature and expiration
        return true; // Always valid for demonstration
    }
}

class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Dummy implementation: In a real app, load from database
        // This user has "USER" and "AGENT" roles for demonstration
        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password("{noop}password") // {noop} for no password encoding for testing
                .roles("USER", "AGENT") // Assign roles here based on your application's logic
                .build();
    }
}

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil = new JwtUtil(); // Inject proper JwtUtil
    private final CustomUserDetailsService userDetailsService = new CustomUserDetailsService(); // Inject proper UserDetailsService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}