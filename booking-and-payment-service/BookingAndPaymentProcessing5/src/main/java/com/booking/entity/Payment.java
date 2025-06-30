package com.booking.entity;

import lombok.Data;
import jakarta.persistence.*;

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
    private Long userId;

    /**The ID of the booking associated with the payment.*/
    private Long bookingId;

    /**The amount of the payment.*/
    private double amount;

    /**The status of the payment.*/
    private String status;

    /**The method of payment, which should be "Credit Card" or "Debit Card".*/
    @Column(nullable = false)
    private String paymentMethod;

    /**The currency of the payment.*/
    private String currency;

    /**The card number used for the payment. This field is not stored in the database.*/
    @Transient
    private String cardNumber;

    /**The CVV code of the card used for the payment. This field is not stored in the database.*/
    @Transient
    private String cvv;

    /**The ATM PIN of the card used for the payment. This field is not stored in the database.*/
    @Transient
    private String atmPin;

    /**The expiry date of the card used for the payment. This field is not stored in the database.*/
    @Transient
    private String expiryDate;
}
