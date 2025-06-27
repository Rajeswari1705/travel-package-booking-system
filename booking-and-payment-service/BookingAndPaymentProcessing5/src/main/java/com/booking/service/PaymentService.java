package com.booking.service;

import com.booking.client.TravelPackageClient;
import com.booking.client.TravelInsuranceClient;
import com.booking.dto.OfferDTO;
import com.booking.dto.TravelPackageDTO;
import com.booking.entity.Booking;
import com.booking.entity.Payment;
import com.booking.repository.BookingRepository;
import com.booking.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TravelPackageClient travelPackageClient;

    @Autowired
    private TravelInsuranceClient travelInsuranceClient;

    public Payment processPayment(Payment payment, String couponCodeApplied) {

        Long bookingId = payment.getBookingId();
        Booking booking = bookingRepo.findById(bookingId).orElse(null);
        if (booking == null) {
            throw new IllegalArgumentException("Booking with ID " + bookingId + " not found.");
        }

        TravelPackageDTO pkg = travelPackageClient.getPackageById(booking.getPackageId());
        if (pkg == null) {
            throw new IllegalArgumentException("Invalid package ID associated with booking.");
        }

        double packagePrice = pkg.getPrice();
        
        //System.out.println("Booking insuranceId: " + booking.getInsuranceId());
        double insurancePrice = 0.0;
        
        if (booking.getInsuranceId() != null && booking.getInsuranceId() > 0) {
        	try {
        		insurancePrice = travelInsuranceClient.getInsurancePriceByInsuranceId(booking.getInsuranceId().intValue());
        		} catch (Exception e) {
        			throw new IllegalArgumentException("Failed to fetch insurance price. Verify Insurance Service is reachable.");
        		}

        }
        

        double discountAmount = 0.0;
        if (couponCodeApplied != null && pkg.getOffer() != null) {
            OfferDTO offer = pkg.getOffer();
            if (couponCodeApplied.equalsIgnoreCase(offer.getCouponCode()) && offer.isActive()) {
                discountAmount = (packagePrice * offer.getDiscountPercentage()) / 100.0;
                System.out.println("Coupon applied. Discount amount: " + discountAmount);
            }
        }

        double expectedTotal = packagePrice + insurancePrice - discountAmount;

        if (Math.abs(payment.getAmount() - expectedTotal) > 0.01) {
            throw new IllegalArgumentException("Payment amount mismatch. Expected: " + expectedTotal);
        }

        if (!payment.getPaymentMethod().equalsIgnoreCase("Credit Card") &&
            !payment.getPaymentMethod().equalsIgnoreCase("Debit Card")) {
            throw new IllegalArgumentException("Only Credit Card or Debit Card accepted.");
        }

        if (payment.getCardNumber() == null || !payment.getCardNumber().matches("\\d{16}")) {
            throw new IllegalArgumentException("Invalid card number.");
        }

        if (payment.getCvv() == null || !payment.getCvv().matches("\\d{3}")) {
            throw new IllegalArgumentException("Invalid CVV.");
        }

        if (payment.getAtmPin() == null || !payment.getAtmPin().matches("\\d{4}")) {
            throw new IllegalArgumentException("Invalid ATM PIN.");
        }

        if (payment.getExpiryDate() == null || !payment.getExpiryDate().matches("\\d{2}/\\d{2}")) {
            throw new IllegalArgumentException("Expiry date format invalid.");
        }

        String[] parts = payment.getExpiryDate().split("/");
        int expMonth = Integer.parseInt(parts[0]);
        int expYear = 2000 + Integer.parseInt(parts[1]);

        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();

        if (expMonth < 1 || expMonth > 12 || expYear < currentYear ||
           (expYear == currentYear && expMonth < currentMonth)) {
            throw new IllegalArgumentException("Card has expired.");
        }

        payment.setStatus("PAID");
        Payment savedPayment = paymentRepo.save(payment);

        booking.setPaymentId(savedPayment.getPaymentId());
        bookingRepo.save(booking);

        if (insurancePrice > 0.0) {
            travelInsuranceClient.updateInsuranceBookingId(booking.getInsuranceId().intValue(), booking.getBookingId());
        }

        notificationService.notifyCustomer(booking, savedPayment);
        notificationService.notifyTravelAgent(booking, savedPayment);

        return savedPayment;
    }

    public double calculateExpectedTotal(Long bookingId, String couponCodeApplied) {
        Booking booking = bookingRepo.findById(bookingId).orElse(null);
        if (booking == null) throw new IllegalArgumentException("Booking not found");

        TravelPackageDTO pkg = travelPackageClient.getPackageById(booking.getPackageId());
        if (pkg == null) throw new IllegalArgumentException("Package not found");

        double packagePrice = pkg.getPrice();

        double insurancePrice = 0.0;
        if (booking.getInsuranceId() != null && booking.getInsuranceId() > 0) {
            insurancePrice = travelInsuranceClient.getInsurancePriceByInsuranceId(booking.getInsuranceId().intValue());
        }

        double discountAmount = 0.0;
        if (couponCodeApplied != null && pkg.getOffer() != null) {
            OfferDTO offer = pkg.getOffer();
            if (couponCodeApplied.equalsIgnoreCase(offer.getCouponCode()) && offer.isActive()) {
                discountAmount = (packagePrice * offer.getDiscountPercentage()) / 100.0;
            }
        }

        return packagePrice + insurancePrice - discountAmount;
    }

    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepo.findById(id).orElse(null);
    }
}