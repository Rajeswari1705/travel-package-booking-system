package com.example.usermanagementservice.dto;
 
public class LoginResponse {
    private String token;
    private String role;
    private Long id;
 
    public LoginResponse(String token, String role, Long id) {
        this.token = token;
        this.role = role;
        this.id = id;
    }
 
    // Getters only
    public String getToken() {
        return token;
    }
 
    public String getRole() {
        return role;
    }
    
    public Long getId() {
    	return id;
    }
}
 