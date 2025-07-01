package com.example.usermanagementservice.service;
 

import com.example.usermanagementservice.dto.UserRoleCountResponse;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.repository.UserRepository;
import com.example.usermanagementservice.service.impl.UserServiceImpl;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
 
import java.util.*;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class UserServiceImplTest {
 
    @InjectMocks
    private UserServiceImpl userService;
 
    @Mock
    private UserRepository userRepository;
 
    @Mock
    private JavaMailSender mailSender;
 
    @Captor
    ArgumentCaptor<SimpleMailMessage> mailCaptor;
 
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);
 
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
 
    // ✅ 1. Register user
    @Test
    void testRegisterUser() {
        User user = new User();
        user.setName("TestUser");
 
when(userRepository.save(any(User.class))).thenReturn(user);
 
        User result = userService.registerUser(user);
 
        assertNotNull(result);
        assertEquals("TestUser", result.getName());
        verify(userRepository).save(user);
logger.info("✅ Register user test passed");
    }
 
    // ✅ 2. Get all users
    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        List<User> users = userService.getAllUsers();
 
        assertEquals(2, users.size());
logger.info("✅ Get all users test passed");
    }
 
    // ✅ 3. Get user by email
    @Test
    void testGetUserByEmail() {
        User user = new User();
        user.setEmail("abc@gmail.com");
 
        when(userRepository.findByEmail("abc@gmail.com")).thenReturn(Optional.of(user));
 
        User result = userService.getUserByEmail("abc@gmail.com");
 
        assertEquals("abc@gmail.com", result.getEmail());
logger.info("✅ Get user by email test passed");
    }
 
    // ✅ 4. Delete user
    @Test
    void testDeleteUser() {
        Long userId = 1L;
 
        doNothing().when(userRepository).deleteById(userId);
        userService.deleteUser(userId);
 
        verify(userRepository, times(1)).deleteById(userId);
logger.info("✅ Delete user test passed");
    }
 
    // ✅ 5. Update profile with old password
    @Test
    void testUpdateUserProfile() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setPassword("oldpass");
 
        User updatedUser = new User();
        updatedUser.setName("New Name");
 
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
when(userRepository.save(any(User.class))).thenReturn(updatedUser);
 
        User result = userService.updateUserProfile(1L, updatedUser, "oldpass", "newpass");
 
        assertEquals("New Name", result.getName());
logger.info("✅ Update user profile test passed");
    }
 
    // ✅ 6. Get user by ID
    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
 
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
 
        User result = userService.getUserById(1L);
 
        assertEquals(1L, result.getId());
logger.info("✅ Get user by ID test passed");
    }
 
    // ✅ 7. Admin update user
    @Test
    void testAdminUpdateUser() {
        User existing = new User();
        existing.setName("Old");
 
        User updated = new User();
        updated.setName("Updated");
 
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
when(userRepository.save(any(User.class))).thenReturn(updated);
 
        User result = userService.adminUpdateUserProfile(1L, updated);
        assertEquals("Updated", result.getName());
logger.info("✅ Admin update user test passed");
    }
 
    // ✅ 8. Count roles
    @Test
    void testUserRoleCounts() {
        when(userRepository.count()).thenReturn(6L); // including admin
        when(userRepository.countByRole("AGENT")).thenReturn(2L);
        when(userRepository.countByRole("CUSTOMER")).thenReturn(3L);
 
        UserRoleCountResponse result = userService.getUserRoleCounts();
 
        assertEquals(5, result.getTotalUsers()); // excluding 1 admin
        assertEquals(2, result.getAgentCount());
        assertEquals(3, result.getCustomerCount());
logger.info("✅ Get role counts test passed");
    }
 
    
 
    // Send Forgot Password Email
    @Test
    void testSendForgotPasswordEmail() {
        User user = new User();
        user.setEmail("demo@gmail.com");
        user.setPassword("12345");
 
        when(userRepository.findByEmail("demo@gmail.com")).thenReturn(Optional.of(user));
 
        userService.sendForgotPasswordEmail("demo@gmail.com");
 
        verify(mailSender, times(1)).send(mailCaptor.capture());
        SimpleMailMessage sentMail = mailCaptor.getValue();
 
        assertEquals("demo@gmail.com", sentMail.getTo()[0]);
        assertTrue(sentMail.getText().contains("12345")); // password in email
logger.info("✅ Forgot password email sent test passed");
    }
}