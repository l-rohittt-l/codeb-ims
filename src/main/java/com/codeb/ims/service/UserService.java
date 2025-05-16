package com.codeb.ims.service;

import java.util.List;

import com.codeb.ims.dto.UpdateProfileDto;
import com.codeb.ims.dto.UserProfileDto;
import com.codeb.ims.dto.UserRegistrationDto;
import com.codeb.ims.model.User;

public interface UserService {
    boolean registerUser(UserRegistrationDto userDto);
    User login(String email, String rawPassword);

    // 👇 Add this for forgot-password token
    String generateResetToken(String email);
    
    boolean resetPassword(String token, String newPassword);
    
    UserProfileDto getUserProfile(String email);

    boolean updateUserProfile(String email, UpdateProfileDto dto);

    boolean promoteUserToAdmin(Long userId);
    List<User> getSalesUsers();  // Add this to UserService interface
    boolean changeUserRole(Long userId, String newRole);
    List<User> getUsersByRole(String role);

}