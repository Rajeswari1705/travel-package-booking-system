package com.example.travelinsuranceservice.controller;
 
import com.example.travelinsuranceservice.model.CoverageType;
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
 
import java.util.List;
 
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
/**
 * Test class for InsuranceController
 */
@WebMvcTest(controllers = InsuranceController.class)
public class InsuranceControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private InsuranceService insuranceService;
 
    @Test
    void testGetInsuranceByUserId() throws Exception {
        Insurance insurance = new Insurance();
        insurance.setInsuranceId(1);
        insurance.setUserId(101L);
        insurance.setCoverageType(CoverageType.BASIC);
 
        when(insuranceService.getUserInsurance(101L)).thenReturn(List.of(insurance));
 
        mockMvc.perform(get("/api/insurance/user/101"))
                .andExpect(status().isOk());
    }
}
 