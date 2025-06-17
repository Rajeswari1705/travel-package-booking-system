package com.booking.DTO;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

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
    private OfferDTO offer;
}
