package com.example.usermanagementservice.dto;

public class SightseeingDTO {
    private String location;
    private String description;

    // Constructors
    public SightseeingDTO() {}

    public SightseeingDTO(String location, String description) {
        this.location = location;
        this.description = description;
    }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    // Getters & Setters
}
