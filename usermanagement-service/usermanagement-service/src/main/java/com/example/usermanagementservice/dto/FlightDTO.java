package com.example.usermanagementservice.dto;

public class FlightDTO {
    private String airline;
    private String fromCity;
    private String toCity;
    private String departureTime;
    private String arrivalTime;

    // Constructors
    public FlightDTO() {}

    public FlightDTO(String airline, String fromCity, String toCity, String departureTime, String arrivalTime) {
        this.airline = airline;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

    
    
}
