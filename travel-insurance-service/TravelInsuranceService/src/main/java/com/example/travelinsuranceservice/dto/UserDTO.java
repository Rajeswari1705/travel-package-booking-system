package com.example.travelinsuranceservice.dto;
 
import lombok.Data;
 
/**
 * Used to receive user details from the User module.
 */
@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
}
 