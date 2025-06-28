package com.booking.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * Data Transfer Object for booking.
 * Used to transfer booking data between different layers of the application.
 */
@Data
public class BookingDTO {
    private Long bookingId;
    private Long userId;
    private Long packageId;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;
    private String status;
    private Long paymentId;
	private Integer insuranceId;
		
}
