package com.booking.controller;

<<<<<<< HEAD
import com.booking.DTO.PackageDTO;
import com.booking.DTO.UserDTO;
import com.booking.client.TravelPackageClient;
import com.booking.client.UserClient;
=======
import com.booking.DTO.BookingDTO;
>>>>>>> bb6a3afb12f3bc8e991cc02d0b275e93e06ff9e8
import com.booking.entity.Booking;

import com.booking.service.BookingService;

import feign.FeignException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private TravelPackageClient packageClient;
	
	
	
	
	
    @Autowired
    private BookingService bookingService;

<<<<<<< HEAD
   /* @PostMapping
    public Booking create(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
=======
//    @PostMapping
//    public Booking create(@RequestBody Booking booking) {
//        return bookingService.createBooking(booking);
//    }
//    
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody Booking booking) {
        BookingDTO bookingDTO = bookingService.createBooking(booking);
        return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
>>>>>>> bb6a3afb12f3bc8e991cc02d0b275e93e06ff9e8
    }

    @GetMapping
    public List<Booking> getAll() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

   @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }
<<<<<<< HEAD
    */
    
    
    
    
    //to get all packages if customer is a valid customer
    @GetMapping("/{userId}/allpackages")
    public ResponseEntity<?> getAllPackagesForUser(@PathVariable Long userId) {
        try {
            // 1. Validate user
            UserDTO user = userClient.getUserById(userId);
 
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
 
            // 2. Fetch packages
            List<PackageDTO> packages = packageClient.getAllPackages();
 
            return ResponseEntity.ok(packages);
        } catch (FeignException.NotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found in User Service");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong: " + e.getMessage());
        }
    }
=======
    
>>>>>>> bb6a3afb12f3bc8e991cc02d0b275e93e06ff9e8


}
