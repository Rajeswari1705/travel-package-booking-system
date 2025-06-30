package com.booking.dto;

import lombok.Data;
/**
 * DTO representing the response after processing a payment.
 * This is sent back to the client or other services with payment result details.
 */
@Data
public class PaymentResponseDTO {
    private Long paymentId;
    private Long userId;
    private Long bookingId;
    private double amount;
    private String status;
    private String paymentMethod;
    private String currency;

}