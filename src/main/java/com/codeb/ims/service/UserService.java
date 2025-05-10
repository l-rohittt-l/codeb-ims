package com.codeb.ims.service;

import com.codeb.ims.dto.UserRegistrationDto;
import com.codeb.ims.model.User;

public interface UserService {
    boolean registerUser(UserRegistrationDto userDto);
    User login(String email, String rawPassword);

    // 👇 Add this for forgot-password token
    String generateResetToken(String email);
    
    boolean resetPassword(String token, String newPassword);

}