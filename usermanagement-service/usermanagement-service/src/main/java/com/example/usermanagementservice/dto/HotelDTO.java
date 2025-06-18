package com.example.usermanagementservice.dto;

public class HotelDTO {
    private String name;
    private String city;
    private double rating;
    private int nights;
    private double costPerNight;

    // Constructors
    public HotelDTO() {}

    public HotelDTO(String name, String city, double rating, int nights, double costPerNight) {
        this.name = name;
        this.city = city;
        this.rating = rating;
        this.nights = nights;
        this.costPerNight = costPerNight;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public double getCostPerNight() {
		return costPerNight;
	}

	public void setCostPerNight(double costPerNight) {
		this.costPerNight = costPerNight;
	}

    // Getters & Setters
}
