package com.example.travelinsuranceservice.controller;
 
import com.example.travelinsuranceservice.dto.InsuranceRequestDTO;
import com.example.travelinsuranceservice.model.CoverageType;
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.service.InsuranceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
 
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class InsuranceControllerTest {
 
    @Mock
    private InsuranceService service;
 
    @InjectMocks
    private InsuranceController controller;
 
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
 
    @SuppressWarnings("deprecation")
	@Test
    void testCreateInsurance() {
        InsuranceRequestDTO dto = new InsuranceRequestDTO();
        dto.setUserId(1L);
        dto.setCoverageType("PREMIUM");
 
        Insurance insurance = new Insurance();
        insurance.setInsuranceId(1);
        insurance.setCoverageType(CoverageType.PREMIUM);
 
        when(service.createInsurance(dto)).thenReturn(insurance);
 
        ResponseEntity<Insurance> response = controller.createInsurance(dto);
 
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(CoverageType.PREMIUM, response.getBody().getCoverageType());
    }
 
    @SuppressWarnings("deprecation")
	@Test
    void testGetInsuranceByUser() {
        Insurance insurance = new Insurance();
        insurance.setUserId(1L);
 
        when(service.getUserInsurance(1L)).thenReturn(List.of(insurance));
 
        ResponseEntity<List<Insurance>> response = controller.getByUser(1L);
 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().get(0).getUserId());
    }
}
 