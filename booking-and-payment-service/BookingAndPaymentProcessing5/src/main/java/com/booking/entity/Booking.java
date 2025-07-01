package com.booking.entity;

import java.time.LocalDate;
import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
/**
 * Entity class representing a Booking.
 * This class maps to the booking table in the database and contains details about a booking.
 */
@Data
@Entity
public class Booking {
    /**The ID of the booking.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    /** The ID of the user who made the booking.*/
    @NotNull
    private Long userId;

    /**The ID of the travel package associated with the booking.*/
    @NotNull
    private Long packageId;

    /**The start date of the trip.*/
    @Column(name = "trip_start_date")
    private LocalDate tripStartDate;

    /**The end date of the trip.*/
    @Column(name = "trip_end_date")
    private LocalDate tripEndDate;

    /**The status of the booking.*/
    private String status;

    /**The ID of the payment associated with the booking.*/
    private Long paymentId;

    /**The ID of the insurance associated with the booking.*/
    private Integer insuranceId;
}
