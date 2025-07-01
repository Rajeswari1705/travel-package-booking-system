package com.example.service;
 
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
 
import com.example.dto.UserDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.TravelPackage;
import com.example.repository.TravelPackageRepository;
import com.example.client.UserClient;
 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
 
import java.util.Optional;
import java.util.Collections;
 
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
@ExtendWith(MockitoExtension.class)
class TravelPackageServiceTest {
 
    @Mock
    private TravelPackageRepository repository;
 
    @Mock
    private UserClient userClient;
 
    @InjectMocks
    private TravelPackageService service;
 
    @Test
    void testGetPackageById_found() {
        TravelPackage pkg = new TravelPackage();
        pkg.setPackageId(1L);
        pkg.setTitle("Sample Package");
 
        when(repository.findById(1L)).thenReturn(Optional.of(pkg));
 
        TravelPackage result = service.getPackageById(1L);
        assertEquals("Sample Package", result.getTitle());
    }
 
    @Test
    void testGetPackageById_notFound() {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getPackageById(2L));
    }
 
    @Test
    void testCreatePackage_validAgent() {
        TravelPackage pkg = new TravelPackage();
        pkg.setAgentId(10L);
 
        UserDTO agent = new UserDTO();
        agent.setId(10L);
        agent.setRole("AGENT");
 
        when(userClient.getUserById(10L)).thenReturn(agent);
when(repository.save(pkg)).thenReturn(pkg);
 
        TravelPackage created = service.createPackage(pkg);
        assertEquals(pkg, created);
    }
}