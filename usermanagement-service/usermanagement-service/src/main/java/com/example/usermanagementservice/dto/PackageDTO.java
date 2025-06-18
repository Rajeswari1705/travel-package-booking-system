package com.example.usermanagementservice.dto;

import java.util.List;

public class PackageDTO {
    private Long agentId;
    private String title;
    private String description;
    private int duration;
    private double price;
    private int maxCapacity;
    private String tripStartDate;
    private String tripEndDate;
    private List<String> highlights;
    private List<FlightDTO> flights;
    private List<HotelDTO> hotels;
    private List<SightseeingDTO> sightseeing;
    private List<ItineraryDTO> itinerary;
    private OfferDTO offer;

    // Constructors
    public PackageDTO() {}

    public PackageDTO(Long agentId, String title, String description, int duration, double price,
                      int maxCapacity, String tripStartDate, String tripEndDate, List<String> highlights,
                      List<FlightDTO> flights, List<HotelDTO> hotels, List<SightseeingDTO> sightseeing,
                      List<ItineraryDTO> itinerary, OfferDTO offer) {
        this.agentId = agentId;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.highlights = highlights;
        this.flights = flights;
        this.hotels = hotels;
        this.sightseeing = sightseeing;
        this.itinerary = itinerary;
        this.offer = offer;
    }

    // Getters & Setters
    public Long getAgentId() { return agentId; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public String getTripStartDate() { return tripStartDate; }
    public void setTripStartDate(String tripStartDate) { this.tripStartDate = tripStartDate; }
    public String getTripEndDate() { return tripEndDate; }
    public void setTripEndDate(String tripEndDate) { this.tripEndDate = tripEndDate; }
    public List<String> getHighlights() { return highlights; }
    public void setHighlights(List<String> highlights) { this.highlights = highlights; }
    public List<FlightDTO> getFlights() { return flights; }
    public void setFlights(List<FlightDTO> flights) { this.flights = flights; }
    public List<HotelDTO> getHotels() { return hotels; }
    public void setHotels(List<HotelDTO> hotels) { this.hotels = hotels; }
    public List<SightseeingDTO> getSightseeing() { return sightseeing; }
    public void setSightseeing(List<SightseeingDTO> sightseeing) { this.sightseeing = sightseeing; }
    public List<ItineraryDTO> getItinerary() { return itinerary; }
    public void setItinerary(List<ItineraryDTO> itinerary) { this.itinerary = itinerary; }
    public OfferDTO getOffer() { return offer; }
    public void setOffer(OfferDTO offer) { this.offer = offer; }
}
