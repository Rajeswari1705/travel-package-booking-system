package com.booking.DTO;

import lombok.Data;

@Data
public class OfferDTO {
    private String couponCode;
    private String description;
    private int discountPercentage;
    private boolean active;
}
