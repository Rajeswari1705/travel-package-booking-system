package com.booking.service;

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

    public Booking createBooking(Booking booking) {


    	ApiResponse response = travelPackageClient.getPackageById(booking.getPackageId());
        Object data = response.getData();

        // Convert the response data to TravelPackageDTO
        TravelPackageDTO travelPackage = objectMapper.convertValue(data, TravelPackageDTO.class);

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

    public List<Booking> getBookingsByPackageId(Long packageId) {
        return bookingRepo.findByPackageId(packageId);
    }
    public List<TravelPackageDTO> findPackagesByTitle(String title) {
        ApiResponse response = travelPackageClient.searchByTitle(title);
        return castToTravelPackageDTOList(response.getData());
    }

    public List<TravelPackageDTO> findPackagesByPrice(double maxPrice) {
        ApiResponse response = travelPackageClient.searchByPrice(maxPrice);
        return castToTravelPackageDTOList(response.getData());
    }

    public List<TravelPackageDTO> findPackagesByOffer(String couponCode) {
        ApiResponse response = travelPackageClient.searchByOffer(couponCode);
        return castToTravelPackageDTOList(response.getData());
    }
//get packages by Id

    public TravelPackageDTO getPackageById(Long id) {
        ApiResponse response = travelPackageClient.getPackageById(id);
        Object data = response.getData();
        if (data instanceof TravelPackageDTO) {
            return (TravelPackageDTO) data;
        }
        throw new RuntimeException("Invalid response format");
    }



}

