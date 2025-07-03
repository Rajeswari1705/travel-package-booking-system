package com.booking.service;

import com.booking.client.TravelInsuranceClient;
import com.booking.client.TravelPackageClient;
import com.booking.client.UserClient;
import com.booking.dto.BookingDTO;
import com.booking.dto.TravelPackageDTO;
import com.booking.dto.UserDTO;
import com.booking.entity.Booking;
import com.booking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepo;

    @Mock
    private TravelPackageClient travelPackageClient;

    @Mock
    private TravelInsuranceClient travelInsuranceClient;

    @Mock
    private UserClient userClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking_validInput_success() {
        Booking bookingRequest = new Booking();
        bookingRequest.setUserId(1L);
        bookingRequest.setPackageId(101L);
        bookingRequest.setInsuranceId(5);

        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setRole("CUSTOMER");

        TravelPackageDTO travelPackage = new TravelPackageDTO();
        travelPackage.setPackageId(101L);
        travelPackage.setTripStartDate(LocalDate.now().plusDays(10));
        travelPackage.setTripEndDate(LocalDate.now().plusDays(15));

        Booking savedBooking = new Booking();
        savedBooking.setBookingId(10L);
        savedBooking.setUserId(1L);
        savedBooking.setPackageId(101L);
        savedBooking.setInsuranceId(5);
        savedBooking.setTripStartDate(travelPackage.getTripStartDate());
        savedBooking.setTripEndDate(travelPackage.getTripEndDate());
        savedBooking.setStatus("PENDING");

        when(userClient.getCustomerById(1L)).thenReturn(user);
        when(travelPackageClient.getPackageById(101L)).thenReturn(travelPackage);
        when(travelInsuranceClient.validateInsurance(5)).thenReturn(true);
        when(bookingRepo.save(any(Booking.class))).thenReturn(savedBooking);

        BookingDTO result = bookingService.createBooking(bookingRequest);

        assertEquals(10L, result.getBookingId());
        assertEquals("PENDING", result.getStatus());
    }

    @Test
    void getAllBookings_returnsList() {
        when(bookingRepo.findAll()).thenReturn(List.of(new Booking()));
        assertFalse(bookingService.getAllBookings().isEmpty());
    }

    @Test
    void getBookingById_notFound_returnsNull() {
        when(bookingRepo.findById(99L)).thenReturn(Optional.empty());
        assertNull(bookingService.getBookingById(99L));
    }

    @Test
    void cancelBooking_successful() {
        Booking booking = new Booking();
        booking.setBookingId(1L);
        booking.setTripStartDate(LocalDate.now().plusDays(10));
        booking.setStatus("PENDING");

        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));

        ResponseEntity<String> response = bookingService.cancelBooking(1L);
        assertEquals("Booking cancelled successfully.", response.getBody());
    }

    @Test
    void cancelBooking_tooLate_returnsBadRequest() {
        Booking booking = new Booking();
        booking.setBookingId(1L);
        booking.setTripStartDate(LocalDate.now().plusDays(5)); // less than 7 days

        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));

        ResponseEntity<String> response = bookingService.cancelBooking(1L);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Cancellation not allowed"));
    }

    @Test
    void hasUserCompletedPackage_trueCase() {
        Booking booking = new Booking();
        booking.setPackageId(200L);
        booking.setStatus("CONFIRMED");
        booking.setTripEndDate(LocalDate.now().minusDays(1));

        when(bookingRepo.findByUserId(1L)).thenReturn(List.of(booking));
        boolean result = bookingService.hasUserCompletedPackage(1L, "200");

        assertTrue(result);
    }

    @Test
    void getBookingsByUserId_returnsList() {
        when(bookingRepo.findByUserId(1L)).thenReturn(List.of(new Booking()));
        assertFalse(bookingService.getBookingsByUserId(1L).isEmpty());
    }

    @Test
    void getAllPackages_returnsList() {
        TravelPackageDTO dto = new TravelPackageDTO();
        dto.setPackageId(1L);
        when(travelPackageClient.getAllPackages()).thenReturn(List.of(dto));

        assertEquals(1, bookingService.getAllPackages().size());
    }

    @Test
    void getPackageById_returnsPackage() {
        TravelPackageDTO pkg = new TravelPackageDTO();
        pkg.setPackageId(5L);
        when(travelPackageClient.getPackageById(5L)).thenReturn(pkg);

        assertEquals(5L, bookingService.getPackageById(5L).getPackageId());
    }
}