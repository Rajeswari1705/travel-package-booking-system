package com.example.usermanagementservice.dto;

public class UserDTO {
	private Long id;
	private String name;
	private String email;
	private String contactNumber;
	private String role;

	// Constructors
	public UserDTO() {
	}

	public UserDTO(Long id, String name, String email, String contactNumber, String role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.contactNumber= contactNumber;
		this.role = role;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}