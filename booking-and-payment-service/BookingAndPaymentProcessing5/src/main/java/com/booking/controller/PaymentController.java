package com.booking.controller;

import com.booking.dto.PaymentResponseDTO;
import com.booking.entity.Payment;
import com.booking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired private PaymentService paymentService;

    // Process payment with optional coupon code
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
    
 // Show total payable before actual payment
    @GetMapping("/expected-total")
    public Map<String, Double> getExpectedTotal(
            @RequestParam Long bookingId,
            @RequestParam(required = false) String couponCode) {
     
        double total = paymentService.calculateExpectedTotal(bookingId, couponCode);
        return Collections.singletonMap("totalPayable", total);
    }
    
    @GetMapping public List<Payment> getAll() { return paymentService.getAllPayments(); }
    
    @GetMapping("/{id}") public Payment getById(@PathVariable Long id) { return paymentService.getPaymentById(id); }
}
