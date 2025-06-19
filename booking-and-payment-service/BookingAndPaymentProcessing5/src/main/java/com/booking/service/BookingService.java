package com.booking.service;

import com.booking.client.TravelPackageClient;
import com.booking.DTO.TravelPackageDTO;
import com.booking.entity.Booking;
import com.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
	
    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private TravelPackageClient travelPackageClient;
    
    public Booking createBooking(Booking booking) {
    	TravelPackageDTO travelPackage = travelPackageClient.getPackageById(booking.getPackageId());
    	
    	booking.setStartDate(travelPackage.getTripStartDate());
    	booking.setEndDate(travelPackage.getTripEndDate());
        booking.setStatus("CONFIRMED");
        
        return bookingRepo.save(booking);
    }
    
    public Booking confirmBooking(Long bookingId) {
    	Booking booking = bookingRepo.findById(bookingId).orElse(null);
    	if (booking != null && booking.getStatus().equals("PENDING_PAYMENT")) {
    		return bookingRepo.save(booking);
    	}
    	return null;
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
    
    //Customers can cancel bookings up to 7 days before departure
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

}