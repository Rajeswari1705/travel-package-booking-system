package com.example.usermanagementservice.dto;

public class OfferDTO {
    private String couponCode;
    private String description;
    private int discountPercentage;
    private boolean active;

    // Constructors
    public OfferDTO() {}

    public OfferDTO(String couponCode, String description, int discountPercentage, boolean active) {
        this.couponCode = couponCode;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.active = active;
    }

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

    // Getters & Setters
}
