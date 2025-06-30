package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.client.UserClient;
import com.example.travelinsuranceservice.dto.AssistanceRequestDTO;
import com.example.travelinsuranceservice.dto.UserDTO;
import com.example.travelinsuranceservice.exception.InvalidInputException;
import com.example.travelinsuranceservice.model.AssistanceRequest;
import com.example.travelinsuranceservice.repository.AssistanceRequestRepository;
import com.example.travelinsuranceservice.service.AssistanceRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
 
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class AssistanceRequestServiceTest {
 
    @Mock
    private AssistanceRequestRepository repo;
 
    @Mock
    private UserClient userClient;
 
    @InjectMocks
    private AssistanceRequestService service;
 
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testCreateAssistanceSuccess() {
        AssistanceRequestDTO dto = new AssistanceRequestDTO();
        dto.setUserId(1L);
        dto.setIssueDescription("Need help");
 
        when(userClient.getUserById(1L)).thenReturn(new UserDTO());
 
        AssistanceRequest saved = new AssistanceRequest();
        saved.setRequestId(101);
        saved.setUserId(1L);
        saved.setIssueDescription("Need help");
 
        when(repo.save(any())).thenReturn(saved);
 
        AssistanceRequest result = service.createRequest(dto);
 
        assertEquals("Need help", result.getIssueDescription());
    }
 
    @Test
    void testCreateAssistanceWithInvalidUser() {
        AssistanceRequestDTO dto = new AssistanceRequestDTO();
        dto.setUserId(999L);
        dto.setIssueDescription("Help!");
 
        when(userClient.getUserById(999L)).thenReturn(null);
 
        assertThrows(InvalidInputException.class, () -> service.createRequest(dto));
    }
 
    @Test
    void testGetRequestsByUser() {
        AssistanceRequest req = new AssistanceRequest();
        req.setUserId(1L);
 
        when(repo.findByUserId(1L)).thenReturn(List.of(req));
 
        List<AssistanceRequest> list = service.getByUser(1L);
 
        assertFalse(list.isEmpty());
        assertEquals(1L, list.get(0).getUserId());
    }
}
 