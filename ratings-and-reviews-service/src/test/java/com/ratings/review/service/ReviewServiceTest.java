package com.ratings.review.service;
 
import com.ratings.review.client.BookingClient;
import com.ratings.review.client.UserClient;
import com.ratings.review.dto.UserDTO;
import com.ratings.review.entity.Review;
import com.ratings.review.exception.ResourceNotFoundException;
import com.ratings.review.repository.ReviewRepository;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
 
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
 
class ReviewServiceTest {
 
    @InjectMocks
    private ReviewService reviewService;
 
    @Mock
    private ReviewRepository reviewRepository;
 
    @Mock
    private UserClient userClient;
 
    @Mock
    private BookingClient bookingClient;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void postReview_whenCustomerAndCompleted_shouldSave() {
        Review review = new Review(1L, 2L, 3L, 5, "Nice trip", LocalDateTime.now());
        UserDTO user = new UserDTO(1L, "John", "CUSTOMER", "email", "pass");
        
        when(userClient.getUserById(1L)).thenReturn(user);
        when(bookingClient.hasCompletedBooking(1L, "3")).thenReturn(true);
        when(reviewRepository.existsByUserIdAndPackageId(1L, 3L)).thenReturn(false);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
 
        Review saved = reviewService.postReview(review);
 
        assertEquals(5, saved.getRating());
        assertEquals("Nice trip", saved.getComment());
    }
 
    @Test
    void postReview_whenUserNotCustomer_shouldThrow() {
        Review review = new Review(1L, 2L, 3L, 5, "Nice trip", LocalDateTime.now());
        UserDTO user = new UserDTO(1L, "John", "AGENT", "email", "pass");
 
        when(userClient.getUserById(1L)).thenReturn(user);
 
        assertThrows(ResourceNotFoundException.class, () -> reviewService.postReview(review));
    }
 
    @Test
    void postReview_whenBookingNotCompleted_shouldThrow() {
        Review review = new Review(1L, 2L, 3L, 5, "Nice trip", LocalDateTime.now());
        UserDTO user = new UserDTO(1L, "John", "CUSTOMER", "email", "pass");
 
        when(userClient.getUserById(1L)).thenReturn(user);
        when(bookingClient.hasCompletedBooking(1L, "3")).thenReturn(false);
 
        assertThrows(ResourceNotFoundException.class, () -> reviewService.postReview(review));
    }
 
    @Test
    void updateReview_shouldModifyFields() {
        Review existing = new Review(1L, 2L, 3L, 4, "Good", LocalDateTime.now());
        Review update = new Review(null, null, null, 5, "Updated", null);
 
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArguments()[0]);
 
        Review result = reviewService.updateReview(1L, update);
 
        assertEquals(5, result.getRating());
        assertEquals("Updated", result.getComment());
    }
 
    @Test
    void getReviewsByPackage_shouldFilter() {
        Review r1 = new Review(1L, 2L, 3L, 4, "Nice", LocalDateTime.now());
        Review r2 = new Review(2L, 2L, 4L, 5, "Super", LocalDateTime.now());
 
        when(reviewRepository.findAll()).thenReturn(List.of(r1, r2));
 
        List<Review> reviews = reviewService.getReviewsByPackage(3L);
        assertEquals(1, reviews.size());
        assertEquals("Nice", reviews.get(0).getComment());
    }
}