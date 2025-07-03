package com.booking.service;

import com.booking.client.TravelInsuranceClient;
import com.booking.client.TravelPackageClient;
import com.booking.dto.OfferDTO;
import com.booking.dto.TravelPackageDTO;
import com.booking.entity.Booking;
import com.booking.entity.Payment;
import com.booking.exception.CustomBusinessException;
import com.booking.repository.BookingRepository;
import com.booking.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepo;

    @Mock
    private BookingRepository bookingRepo;

    @Mock
    private NotificationService notificationService;

    @Mock
    private TravelPackageClient travelPackageClient;

    @Mock
    private TravelInsuranceClient travelInsuranceClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessPayment_Success() {
        // Arrange
        Payment payment = new Payment();
        payment.setBookingId(1L);
        payment.setUserId(1L);
        payment.setAmount(900.0);
        payment.setCardNumber("1234567812345678");
        payment.setCvv("123");
        payment.setAtmPin("1234");
        payment.setExpiryDate("12/30");
        payment.setPaymentMethod("Credit Card");

        Booking booking = new Booking();
        booking.setBookingId(1L);
        booking.setPackageId(10L);
        booking.setInsuranceId((int) 5L);

        TravelPackageDTO pkg = new TravelPackageDTO();
        pkg.setPrice(1000.0);
        OfferDTO offer = new OfferDTO();
        offer.setActive(true);
        offer.setCouponCode("DISCOUNT10");
        offer.setDiscountPercentage(10);
        pkg.setOffer(offer);

        when(paymentRepo.findByBookingIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));
        when(travelPackageClient.getPackageById(10L)).thenReturn(pkg);
        when(travelInsuranceClient.getInsurancePriceByInsuranceId(5)).thenReturn(0.0);
        when(paymentRepo.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Payment saved = paymentService.processPayment(payment, "DISCOUNT10");

        // Assert
        assertEquals("PAID", saved.getStatus());
        verify(notificationService).notifyCustomer(booking, saved);
        verify(notificationService).notifyTravelAgent(booking, saved);
    }

    @Test
    void testProcessPayment_PaymentAlreadyExists() {
        when(paymentRepo.findByBookingIdAndUserId(anyLong(), anyLong()))
                .thenReturn(Optional.of(new Payment()));

        Payment payment = new Payment();
        payment.setBookingId(1L);
        payment.setUserId(1L);

        assertThrows(CustomBusinessException.class, () ->
                paymentService.processPayment(payment, null));
    }

    @Test
    void testCalculateExpectedTotal_WithDiscountAndInsurance() {
        Booking booking = new Booking();
        booking.setBookingId(1L);
        booking.setPackageId(10L);
        booking.setInsuranceId((int) 5L);

        TravelPackageDTO pkg = new TravelPackageDTO();
        pkg.setPrice(1000.0);
        OfferDTO offer = new OfferDTO();
        offer.setCouponCode("SAVE10");
        offer.setDiscountPercentage(10);
        offer.setActive(true);
        pkg.setOffer(offer);

        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));
        when(travelPackageClient.getPackageById(10L)).thenReturn(pkg);
        when(travelInsuranceClient.getInsurancePriceByInsuranceId(5)).thenReturn(100.0);

        double total = paymentService.calculateExpectedTotal(1L, "SAVE10");

        assertEquals(1000 + 100 - 100, total, 0.01);
    }

    @Test
    void testGetPaymentById_NotFound() {
        when(paymentRepo.findById(1L)).thenReturn(Optional.empty());
        assertNull(paymentService.getPaymentById(1L));
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepo.findAll()).thenReturn(Collections.emptyList());
        assertTrue(paymentService.getAllPayments().isEmpty());
    }
}