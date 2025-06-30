package com.ratings.review.service;
 
import com.ratings.review.client.TravelPackageClient;
import com.ratings.review.entity.AgentResponse;
import com.ratings.review.entity.Review;
import com.ratings.review.exception.ResourceNotFoundException;
import com.ratings.review.repository.AgentResponseRepository;
import com.ratings.review.repository.ReviewRepository;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
 
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
 
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockitoAnnotations;
 
class AgentResponseServiceTest {
 
    @InjectMocks
    private AgentResponseService agentResponseService;
 
    @Mock
    private AgentResponseRepository agentResponseRepository;
 
    @Mock
    private ReviewRepository reviewRepository;
 
    @Mock
    private TravelPackageClient travelPackageClient;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void respondToReview_shouldSaveResponse() {
        Review review = new Review(1L, 2L, 10L, 5, "Super", LocalDateTime.now());
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(travelPackageClient.getAgentIdByPackageId(10L)).thenReturn(Map.of("agentId", 20L));
        when(agentResponseRepository.save(any(AgentResponse.class)))
                .thenAnswer(i -> i.getArguments()[0]);
 
        AgentResponse response = agentResponseService.respondToReview(20L, 1L, "Thanks!");
 
        assertEquals("Thanks!", response.getResponseMessage());
        assertNotNull(response.getResponseTime());
    }
 
    @Test
    void respondToReview_wrongAgent_shouldThrow() {
        Review review = new Review(1L, 2L, 10L, 5, "Super", LocalDateTime.now());
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(travelPackageClient.getAgentIdByPackageId(10L)).thenReturn(Map.of("agentId", 99L));
 
        assertThrows(ResourceNotFoundException.class,
            () -> agentResponseService.respondToReview(20L, 1L, "Invalid"));
    }
 
    @Test
    void updateResponse_shouldChangeText() {
        AgentResponse response = new AgentResponse(1L, 20L, 1L, "Old", LocalDateTime.now());
        when(agentResponseRepository.findById(1L)).thenReturn(Optional.of(response));
        when(agentResponseRepository.save(any(AgentResponse.class)))
                .thenAnswer(i -> i.getArguments()[0]);
 
        AgentResponse updated = agentResponseService.updateResponse(1L, "New reply");
 
        assertEquals("New reply", updated.getResponseMessage());
    }
 
    @Test
    void deleteResponse_shouldInvokeDelete() {
        AgentResponse response = new AgentResponse(1L, 20L, 1L, "Old", LocalDateTime.now());
        when(agentResponseRepository.findById(1L)).thenReturn(Optional.of(response));
 
        agentResponseService.deleteResponse(1L);
 
        verify(agentResponseRepository, times(1)).delete(response);
    }
}
 