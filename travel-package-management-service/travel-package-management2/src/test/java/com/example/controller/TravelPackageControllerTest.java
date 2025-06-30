package com.example.controller;
 
import com.example.model.TravelPackage;
import com.example.response.ApiResponse;
import com.example.service.TravelPackageService;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
 
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
 
import java.util.Collections;
 
import static org.junit.jupiter.api.Assertions.*;
 
class TravelPackageControllerTest {
 
    @Mock
    private TravelPackageService service;
 
    @InjectMocks
    private TravelPackageController controller;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testGetAllPackages() {
        when(service.getAllPackages()).thenReturn(Collections.emptyList());
 
        ResponseEntity<ApiResponse> response = controller.getAllPackages();
 
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("All packages retrieved ", response.getBody().getMessage());
    }
 
    @Test
    void testGetById() {
        TravelPackage pkg = new TravelPackage();
        pkg.setPackageId(1L);
        pkg.setTitle("Paris Tour");
 
        when(service.getPackageById(1L)).thenReturn(pkg);
 
        ResponseEntity<ApiResponse> response = controller.getById(1L);
 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Package found", response.getBody().getMessage());
    }
}
 