package com.example.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TravelPackageDTO {
	private Long packageId;
	private Long agentId;
	private String title;
	private String description;
	private int duration;
	private double price;
	private int maxCapacity;
	private LocalDate tripStartDate;
	private LocalDate tripEndDate;
	private List<String> highlights;
	private List<FlightDTO> flights;
	private List<HotelDTO> hotels;
	private List<SightseeingDTO> sightseeing;
	private List<ItineraryDTO> itinerary;
	private String imageUrl; //for images in front-end
	private OfferDTO offer;
	
	//newly added fields
	private String country;
	private String destination;
	private String tripType;
	

}
