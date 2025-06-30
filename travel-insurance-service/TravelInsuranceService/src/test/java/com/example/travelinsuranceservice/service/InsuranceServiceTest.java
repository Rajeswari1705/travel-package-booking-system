package com.example.travelinsuranceservice.service;
 
import com.example.travelinsuranceservice.client.BookingClient;
import com.example.travelinsuranceservice.client.UserClient;
import com.example.travelinsuranceservice.dto.InsuranceRequestDTO;
import com.example.travelinsuranceservice.dto.UserDTO;
import com.example.travelinsuranceservice.exception.InvalidInputException;
import com.example.travelinsuranceservice.model.CoverageType;
import com.example.travelinsuranceservice.model.Insurance;
import com.example.travelinsuranceservice.repository.InsuranceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
 
import java.util.*;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class InsuranceServiceTest {
 
    @Mock
    private InsuranceRepository repo;
 
    @Mock
    private UserClient userClient;
 
    @Mock
    private BookingClient bookingClient;
 
    @InjectMocks
    private InsuranceService service;
 
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testCreateInsuranceSuccess() {
        InsuranceRequestDTO dto = new InsuranceRequestDTO();
        dto.setUserId(1L);
        dto.setCoverageType("STANDARD");
 
        when(userClient.getUserById(1L)).thenReturn(new UserDTO());
        when(repo.findByUserIdAndBookingIdIsNull(1L)).thenReturn(Collections.emptyList());
 
        Insurance mock = new Insurance();
        mock.setInsuranceId(1);
        mock.setCoverageType(CoverageType.STANDARD);
        when(repo.save(any())).thenReturn(mock);
 
        Insurance insurance = service.createInsurance(dto);
 
        assertEquals(1, insurance.getInsuranceId());
        assertEquals(CoverageType.STANDARD, insurance.getCoverageType());
    }
 
    @Test
    void testCreateInsuranceWithInvalidUser() {
        InsuranceRequestDTO dto = new InsuranceRequestDTO();
        dto.setUserId(999L);
        dto.setCoverageType("STANDARD");
 
        when(userClient.getUserById(999L)).thenReturn(null);
 
        assertThrows(InvalidInputException.class, () -> service.createInsurance(dto));
    }
 
    @Test
    void testGetInsuranceByUser() {
        Insurance insurance = new Insurance();
        insurance.setUserId(1L);
        when(repo.findByUserId(1L)).thenReturn(List.of(insurance));
 
        List<Insurance> result = service.getUserInsurance(1L);
 
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getUserId());
    }
}
 