package com.example.travelinsuranceservice.controller;
 
import com.example.travelinsuranceservice.dto.AssistanceRequestDTO;
import com.example.travelinsuranceservice.model.AssistanceRequest;
import com.example.travelinsuranceservice.service.AssistanceRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
 
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class AssistanceRequestControllerTest {
 
    @Mock
    private AssistanceRequestService service;
 
    @InjectMocks
    private AssistanceRequestController controller;
 
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
 
    @SuppressWarnings("deprecation")
	@Test
    void testCreateAssistanceSuccess() {
        AssistanceRequestDTO dto = new AssistanceRequestDTO();
        dto.setUserId(1L);
        dto.setIssueDescription("Medical emergency");
 
        AssistanceRequest expected = new AssistanceRequest();
        expected.setUserId(1L);
        expected.setRequestId(101);
        expected.setIssueDescription("Medical emergency");
 
        when(service.createRequest(dto)).thenReturn(expected);
 
        ResponseEntity<AssistanceRequest> response = controller.requestHelp(dto);
 
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Medical emergency", response.getBody().getIssueDescription());
    }
 
    @SuppressWarnings("deprecation")
	@Test
    void testGetAssistanceByUser() {
        AssistanceRequest req = new AssistanceRequest();
        req.setUserId(1L);
 
        when(service.getByUser(1L)).thenReturn(List.of(req));
 
        ResponseEntity<List<AssistanceRequest>> response = controller.getByUser(1L);
 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().get(0).getUserId());
    }
}
 