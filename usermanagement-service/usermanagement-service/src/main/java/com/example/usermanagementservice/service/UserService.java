//All the business logic like Registering, getting, updating, deleting a user
package com.example.usermanagementservice.service;
//this interface defines what functions are available
import com.example.usermanagementservice.model.User;
import java.util.List;

public interface UserService {
	User registerUser(User user); //Add new user
	List<User> getAllUsers(); // Get all users
	User getUserByEmail(String email); //find user by email
	
	void deleteUser(Long id); //deletes user
	User getUserById(Long id); //View profile
	User updateUserProfile(Long id, User updatedUser);//Update profile like user management in their profile for editing profiles
	
	
}
