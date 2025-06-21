package com.booking.service;

<<<<<<< HEAD
import com.booking.DTO.TravelPackageDTO;
=======
import com.booking.client.TravelPackageClient;
import com.booking.client.UserClient;
import com.booking.DTO.BookingDTO;
import com.booking.DTO.TravelPackageDTO;
import com.booking.DTO.UserDTO;
>>>>>>> bb6a3afb12f3bc8e991cc02d0b275e93e06ff9e8
import com.booking.entity.Booking;
import com.booking.client.*;
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

<<<<<<< HEAD
    /*public Booking createBooking(Booking booking) {
=======
<<<<<<< HEAD
    @Autowired
    private TravelPackageClient travelPackageClient;

    @Autowired
    private UserClient userClient;

    public BookingDTO createBooking(Booking booking) {
        TravelPackageDTO travelPackage = travelPackageClient.getPackageById(booking.getPackageId());
        UserDTO user = userClient.getUserById(booking.getUserId());
        

        if (travelPackage == null || user == null) {
            System.out.println("travelPackage: " + travelPackage);
            System.out.println("user: " + user);
            throw new RuntimeException("Invalid travel package or user");
        }

        booking.setTripStartDate(travelPackage.getTripStartDate());
        booking.setTripEndDate(travelPackage.getTripEndDate());
=======
    public Booking createBooking(Booking booking) {
>>>>>>> bb6a3afb12f3bc8e991cc02d0b275e93e06ff9e8


   	ApiResponse response = TravelPackageClient.getPackageById(booking.getPackageId());
        Object data = response.getData();

        // Convert the response data to TravelPackageDTO
        TravelPackageDTO travelPackage = objectMapper.convertValue(data, TravelPackageDTO.class);

        if (travelPackage == null) {
            throw new IllegalArgumentException("Invalid package ID");
        }


>>>>>>> da78b8b44b03d258736ef3becfb5e869e44cb504
        booking.setStatus("CONFIRMED");

        Booking savedBooking = bookingRepo.save(booking);

        BookingDTO dto = new BookingDTO();
        dto.setBookingId(savedBooking.getBookingId());
        dto.setUserId(savedBooking.getUserId());
        dto.setPackageId(savedBooking.getPackageId());
        dto.setTripStartDate(savedBooking.getTripStartDate());
        dto.setTripEndDate(savedBooking.getTripEndDate());
        dto.setStatus(savedBooking.getStatus());
        dto.setPaymentId(savedBooking.getPaymentId());

        return dto;
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
        if (booking.getTripStartDate().minusDays(7).isBefore(today)) {
            return ResponseEntity.badRequest().body("Cancellation not allowed. Must cancel at least 7 days before departure.");
        }

        booking.setStatus("CANCELLED");
        bookingRepo.save(booking);
        return ResponseEntity.ok("Booking cancelled successfully.");
    }
<<<<<<< HEAD
}
=======




    

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
        ApiResponse response = TravelPackageClient.searchByOffer(couponCode);
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



    }*/
}

>>>>>>> da78b8b44b03d258736ef3becfb5e869e44cb504
