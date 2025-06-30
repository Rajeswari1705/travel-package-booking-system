package com.booking.controller;

import com.booking.dto.PaymentResponseDTO;
import com.booking.entity.Payment;
import com.booking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling payment-related requests.
 * 
 * This controller provides end-points for processing payments, calculating expected totals,
 * and retrieving payment details.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Process a payment with an optional coupon code.
     * 
     * @param payment The payment entity to be processed.
     * @param couponCode The optional coupon code for discount.
     * @return PaymentResponseDTO containing the details of the processed payment.
     */
    @PostMapping
    public PaymentResponseDTO process(
            @RequestBody Payment payment,
            @RequestParam(required = false) String couponCode) {

        Payment savedPayment = paymentService.processPayment(payment, couponCode);

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentId(savedPayment.getPaymentId());
        response.setUserId(savedPayment.getUserId());
        response.setBookingId(savedPayment.getBookingId());
        response.setAmount(savedPayment.getAmount());
        response.setStatus(savedPayment.getStatus());
        response.setPaymentMethod(savedPayment.getPaymentMethod());
        response.setCurrency(savedPayment.getCurrency());

        return response;
    }

    /**
     * Calculate the total payable amount before actual payment.
     * 
     * @param bookingId The ID of the booking.
     * @param couponCode The optional coupon code for discount.
     * @return A map containing the total payable amount.
     */
    @GetMapping("/expected-total")
    public Map<String, Double> getExpectedTotal(
            @RequestParam Long bookingId,
            @RequestParam(required = false) String couponCode) {

        double total = paymentService.calculateExpectedTotal(bookingId, couponCode);
        return Collections.singletonMap("totalPayable", total);
    }

    /**
     * Retrieve all payments.
     * 
     * @return A list of all payments.
     */
    @GetMapping
    public List<Payment> getAll() {
        return paymentService.getAllPayments();
    }

    /**
     * Retrieve a payment by its ID.
     * 
     * @param id The ID of the payment to be retrieved.
     * @return The payment entity.
     */
    @GetMapping("/{id}")
    public Payment getById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }
}
