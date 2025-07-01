package com.example.usermanagementservice.controller;
 
import com.example.usermanagementservice.dto.BookingDTO;
import com.example.usermanagementservice.dto.TravelPackageDTO;
import com.example.usermanagementservice.dto.UserRoleCountResponse;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;
 
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
 
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
public class UserControllerTest {
 
    @Mock
    private UserService userService;
 
    @InjectMocks
    private UserController userController;
 
    private User dummyUser;
 
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dummyUser = new User();
        dummyUser.setId(1L);
        dummyUser.setName("Test User");
        dummyUser.setEmail("test@example.com");
        dummyUser.setRole("CUSTOMER");
    }
 
    @Test
    public void testRegisterUser() {
        when(userService.registerUser(any(User.class))).thenReturn(dummyUser);
 
        ResponseEntity<?> response = userController.registerUser(dummyUser);
 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dummyUser, response.getBody());
    }
 
    @Test
    public void testGetAllUsersAsAdmin() {
        List<User> users = Arrays.asList(dummyUser);
        when(userService.getAllUsers()).thenReturn(users);
 
        HttpServletRequest request = mockRequestWithRole("ADMIN");
 
        ResponseEntity<?> response = userController.getAllUsers(request);
 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(users, response.getBody());
    }
 
    @Test
    public void testGetMyProfile() {
        mockSecurityContextWithEmail("test@example.com");
 
        when(userService.getUserByEmail("test@example.com")).thenReturn(dummyUser);
 
        ResponseEntity<?> response = userController.getMyProfile();
 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dummyUser, response.getBody());
    }
 
 
    @Test
    public void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);
 
        HttpServletRequest request = mockRequestWithRole("ADMIN");
        ResponseEntity<?> response = userController.deleteUser(1L, request);
 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User deleted successfully", response.getBody());
    }
 
    @Test
    public void testGetUserRoleCounts() {
        UserRoleCountResponse countResponse = new UserRoleCountResponse(10L, 4L, 6L);
        when(userService.getUserRoleCounts()).thenReturn(countResponse);
 
        HttpServletRequest request = mockRequestWithRole("ADMIN");
        ResponseEntity<?> response = userController.getUserRoleCounts(request);
 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(countResponse, response.getBody());
    }
  
    @Test
    public void testFetchBookingsByUserId() {
        List<BookingDTO> bookings = Arrays.asList(new BookingDTO());
        when(userService.getBookingsByUserId(1L)).thenReturn(bookings);
 
        HttpServletRequest request = mockRequestWithRole("ADMIN");
        ResponseEntity<?> response = userController.getUserBookings(1L, request);
 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bookings, response.getBody());
    }
 
    // ---------- Utility Methods ----------
 
    // Mocks HttpServletRequest with Authorization Header
    private HttpServletRequest mockRequestWithRole(String role) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer dummy.jwt.token." + role);
        return request;
    }
 
    // Mocks Spring SecurityContext with email for getMyProfile()
    private void mockSecurityContextWithEmail(String email) {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);
 
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
 
        SecurityContextHolder.setContext(context);
    }
}