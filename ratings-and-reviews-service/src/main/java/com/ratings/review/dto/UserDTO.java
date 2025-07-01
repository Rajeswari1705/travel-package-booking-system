package com.ratings.review.dto;
import lombok.Data;
import lombok.AllArgsConstructor; // Add this import
import lombok.NoArgsConstructor;  // Add this import, often good for JPA
@Data
@AllArgsConstructor // Generates constructor with all fields
@NoArgsConstructor  // Generates a no-argument constructor
public class UserDTO {
	private Long id;
    private String name;
    private String email;
    private String role;
}
