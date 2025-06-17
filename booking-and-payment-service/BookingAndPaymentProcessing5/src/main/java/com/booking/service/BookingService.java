package com.booking.service;

import com.booking.client.TravelPackageClient;
import com.booking.DTO.TravelPackageDTO;
import com.booking.entity.Booking;
import com.booking.repository.BookingRepository;
import com.booking.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private TravelPackageClient travelPackageClient;

    public Booking createBooking(Booking booking) {
        TravelPackageDTO travelPackage = travelPackageClient.getPackageById(Long.parseLong(booking.getPackageId()));
        if (travelPackage == null) {
            throw new IllegalArgumentException("Invalid package ID");
        }
        booking.setStatus("CONFIRMED");
        return bookingRepo.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepo.findById(id).orElse(null);
    }

    public void deleteBooking(Long id) {
        bookingRepo.deleteById(id);
    }

    public ResponseEntity<String> cancelBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElse(null);
        if (booking == null) {
            return ResponseEntity.badRequest().body("Booking not found.");
        }

        LocalDate today = LocalDate.now();
        if (booking.getStartDate().minusDays(7).isBefore(today)) {
            return ResponseEntity.badRequest().body("Cancellation not allowed. Must cancel at least 7 days before departure.");
        }

        booking.setStatus("CANCELLED");
        bookingRepo.save(booking);
        return ResponseEntity.ok("Booking cancelled successfully.");
    }

    public List<TravelPackageDTO> findPackagesByTitle(String title) {
        ApiResponse response = travelPackageClient.searchByTitle(title);
        return castToTravelPackageDTOList(response.getData());
    }

 

    private List<TravelPackageDTO> castToTravelPackageDTOList(Object data) {
        if (data instanceof List<?>) {
            return ((List<?>) data).stream()
                    .filter(item -> item instanceof TravelPackageDTO)
                    .map(item -> (TravelPackageDTO) item)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public int getBookingCountByUser(Long userId) {
        return bookingRepo.countByUserId(userId);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepo.findByUserId(userId);
    }

    public List<Booking> getBookingsByPackageId(String packageId) {
        return bookingRepo.findByPackageId(packageId);
    }
    public List<TravelPackageDTO> findPackagesByPrice(double maxPrice) {
        ApiResponse response = travelPackageClient.searchByPrice(maxPrice);

        if (response == null || response.getData() == null) {
            throw new RuntimeException("No data received from Travel Package Service");
        }

        Object data = response.getData();

        if (data instanceof List<?>) {
            return ((List<?>) data).stream()
                .filter(item -> item instanceof TravelPackageDTO)
                .map(item -> (TravelPackageDTO) item)
                .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Unexpected response format: " + data.getClass().getName());
        }
    }

}
