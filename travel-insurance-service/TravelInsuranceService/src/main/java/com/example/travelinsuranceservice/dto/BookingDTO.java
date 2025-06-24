package com.example.travelinsuranceservice.dto;
 
import lombok.Data;
 
import java.time.LocalDate;
 
@Data
public class BookingDTO {
    private Long bookingId;
    private Integer userId;
    private Integer packageId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Integer paymentId;
}
 
