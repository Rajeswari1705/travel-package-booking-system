package com.booking.dto;

import lombok.Data;
/**
 * DTO for transferring user-related data across services.
 */
@Data
public class UserDTO {
	
	private Long id;
	private String name;
	private String email;
	private String role;

}
