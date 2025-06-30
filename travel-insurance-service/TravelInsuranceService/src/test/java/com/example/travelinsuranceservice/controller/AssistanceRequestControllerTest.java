package com.example.travelinsuranceservice.controller;
 
import com.example.travelinsuranceservice.model.AssistanceRequest;
import com.example.travelinsuranceservice.service.AssistanceRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
 
import java.util.List;
 
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
/**
 * Test class for AssistanceRequestController
 */
@WebMvcTest(controllers = AssistanceRequestController.class)
public class AssistanceRequestControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private AssistanceRequestService assistanceService;
 
    @Test
    void testGetRequestsByUserId() throws Exception {
        AssistanceRequest req = new AssistanceRequest();
        req.setRequestId(1);
        req.setUserId(202L);
        req.setIssueDescription("Lost passport");
 
        when(assistanceService.getByUser(202L)).thenReturn(List.of(req));
 
        mockMvc.perform(get("/api/assistance/user/202"))
                .andExpect(status().isOk());
    }
}
 