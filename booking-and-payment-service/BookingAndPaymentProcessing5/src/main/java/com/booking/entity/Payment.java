package com.booking.entity;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Entity class representing a Payment.
 * This class maps to the payment table in the database and contains details about a payment.
 */
@Data
@Entity
public class Payment {
    /**The ID of the payment.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    /**The ID of the user who made the payment.*/
    @NotNull(message = "User ID is required")
    private Long userId;

    /**The ID of the booking associated with the payment.*/
    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    /**The amount of the payment.*/
    @NotNull(message = "Amount is required")
    private double amount;

    /**The status of the payment.*/
    private String status;

    /**The method of payment, which should be "Credit Card" or "Debit Card".*/
    @Column(nullable = false)
    private String paymentMethod;

    /**The currency of the payment.*/
    @NotBlank(message = "Currency is required")
    private String currency;

    /**The card number used for the payment. This field is not stored in the database.*/
    @Transient
    @NotBlank(message = "Card Number is required")
    private String cardNumber;

    /**The CVV code of the card used for the payment. This field is not stored in the database.*/
    @Transient
    @NotBlank(message = "CVV is required")
    private String cvv;

    /**The ATM PIN of the card used for the payment. This field is not stored in the database.*/
    
    @Transient
    @NotBlank(message = "ATM PIN is required")
    private String atmPin;

    /**The expiry date of the card used for the payment. This field is not stored in the database.*/
    @Transient
    @NotBlank(message = "Expiry date is required")
    private String expiryDate;
}
