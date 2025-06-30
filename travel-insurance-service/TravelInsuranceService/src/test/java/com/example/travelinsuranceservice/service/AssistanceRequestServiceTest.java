package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.dto.AssistanceRequestDTO;
import com.example.travelinsuranceservice.dto.UserDTO;
import com.example.travelinsuranceservice.model.AssistanceRequest;
import com.example.travelinsuranceservice.repository.AssistanceRequestRepository;
import com.example.travelinsuranceservice.client.UserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
 
/**
 * Unit test for AssistanceRequestService
 */
public class AssistanceRequestServiceTest {
 
    @InjectMocks
    private AssistanceRequestService assistanceService;
 
    @Mock
    private AssistanceRequestRepository repo;
 
    @Mock
    private UserClient userClient;
 
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testCreateAssistanceRequest() {
        AssistanceRequestDTO dto = new AssistanceRequestDTO();
        dto.setUserId(1L);
        dto.setIssueDescription("Need help");
 
        when(userClient.getUserById(1L)).thenReturn((UserDTO) Map.of("id", 1));
        when(repo.save(any(AssistanceRequest.class))).thenAnswer(i -> {
            AssistanceRequest r = i.getArgument(0);
            r.setRequestId(1);
            return r;
        });
 
        AssistanceRequest result = assistanceService.createRequest(dto);
        assertEquals("Need help", result.getIssueDescription());
        assertEquals(1L, result.getUserId());
    }
}
 