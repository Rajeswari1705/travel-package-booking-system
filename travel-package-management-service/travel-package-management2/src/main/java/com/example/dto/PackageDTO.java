package com.example.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class PackageDTO {
	private Long packageId;
	//private Long agentId;
	private String title;
	private String description;
	private int duration;
	private double price;
	private int maxCapacity;
	private LocalDate tripStartDate;
	private LocalDate tripEndDate;
	
	
	public PackageDTO(Long packageId, String title, String description, int duration, double price, int maxCapacity,
			LocalDate tripStartDate, LocalDate tripEndDate) {
		super();
		this.packageId = packageId;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.price = price;
		this.maxCapacity = maxCapacity;
		this.tripStartDate = tripStartDate;
		this.tripEndDate = tripEndDate;
	}

	
	
}