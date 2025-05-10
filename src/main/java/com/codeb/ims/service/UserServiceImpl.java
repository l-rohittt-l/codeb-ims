package com.codeb.ims.service;

import com.codeb.ims.dto.UserRegistrationDto;
import com.codeb.ims.model.User;
import com.codeb.ims.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.UUID;
import com.codeb.ims.model.PasswordResetToken;
import com.codeb.ims.repository.PasswordResetTokenRepository;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    @Override
    public boolean registerUser(UserRegistrationDto userDto) {
        // Check if email already exists
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return false; // user already exists
        }

        // Create new User from DTO
        User user = new User();
        user.setFull_name(userDto.getFull_name());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hash password

        // Fix: Add "ROLE_" prefix for Spring Security compatibility
        user.setRole("ROLE_" + userDto.getRole().toUpperCase()); // Ensures ROLE_ADMIN / ROLE_SALES

        user.setStatus(userDto.getStatus());

        // Save to DB
        userRepository.save(user);
        return true;
    }

    @Override
    public User login(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user; // Login success
        }

        return null; // Incorrect password
    }
    
    public String generateResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null; // user not found
        }

        // generate unique token
        String token = UUID.randomUUID().toString();

        // expiry in 30 minutes
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(30);

        // store token in DB
        PasswordResetToken resetToken = new PasswordResetToken(token, email, expiry);
        tokenRepository.save(resetToken);

        return token;
    }
    

    @Override
	public boolean resetPassword(String token, String newPassword) {
    Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);

    if (tokenOptional.isEmpty()) {
        return false; // invalid token
    }

    PasswordResetToken resetToken = tokenOptional.get();

    if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
        return false; // token expired
    }

    Optional<User> userOptional = userRepository.findByEmail(resetToken.getEmail());
    if (userOptional.isEmpty()) {
        return false; // user not found
    }

    User user = userOptional.get();
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);

    tokenRepository.delete(resetToken); // token used, remove it

    return true;
}
}
