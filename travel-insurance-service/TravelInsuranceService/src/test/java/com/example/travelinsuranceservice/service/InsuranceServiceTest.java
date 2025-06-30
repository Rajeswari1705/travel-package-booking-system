package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.dto.InsuranceRequestDTO;
import com.example.travelinsuranceservice.dto.UserDTO;
import com.example.travelinsuranceservice.model.CoverageType;
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.repository.InsuranceRepository;
import com.example.travelinsuranceservice.client.UserClient;
import com.example.travelinsuranceservice.client.BookingClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
/**
 * Unit test for InsuranceService
 */
public class InsuranceServiceTest {
 
    @InjectMocks
    private InsuranceService insuranceService;
 
    @Mock
    private InsuranceRepository insuranceRepository;
 
    @Mock
    private UserClient userClient;
 
    @Mock
    private BookingClient bookingClient;
 
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testCreateInsuranceAndFetchPrice() {
        InsuranceRequestDTO dto = new InsuranceRequestDTO();
        dto.setUserId(1L);
        dto.setCoverageType("BASIC");
 
        when(userClient.getUserById(1L)).thenReturn((UserDTO) Map.of("id", 1));
        when(insuranceRepository.findByUserIdAndBookingIdIsNull(1L)).thenReturn(Collections.emptyList());
 
        Insurance mock = new Insurance();
        mock.setUserId(1L);
        mock.setCoverageType(CoverageType.BASIC);
        mock.setPrice(500.0);
        mock.setClaimableAmount(1000.0);
        mock.setInsuranceId(1);
 
        when(insuranceRepository.save(any(Insurance.class))).thenReturn(mock);
 
        Insurance insurance = insuranceService.createInsurance(dto);
 
        assertEquals(CoverageType.BASIC, insurance.getCoverageType());
        assertEquals(1L, insurance.getUserId());
    }
}
 