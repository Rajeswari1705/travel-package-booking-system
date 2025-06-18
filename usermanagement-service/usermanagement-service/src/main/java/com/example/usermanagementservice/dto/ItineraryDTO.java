package com.example.usermanagementservice.dto;

public class ItineraryDTO {
    private int dayNumber;
    private String activityTitle;
    private String activityDescription;

    // Constructors
    public ItineraryDTO() {}

    public ItineraryDTO(int dayNumber, String activityTitle, String activityDescription) {
        this.dayNumber = dayNumber;
        this.activityTitle = activityTitle;
        this.activityDescription = activityDescription;
    }

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public String getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

    // Getters & Setters
}
