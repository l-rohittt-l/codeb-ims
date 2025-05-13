package com.codeb.ims.service;

import com.codeb.ims.dto.UserProfileDto;
import com.codeb.ims.dto.UserRegistrationDto;
import com.codeb.ims.model.User;
import com.codeb.ims.model.PasswordResetToken;
import com.codeb.ims.repository.UserRepository;
import com.codeb.ims.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

	@Value("${app.frontend-url}")
	private String frontendUrl;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean registerUser(UserRegistrationDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return false;
        }

        User user = new User();
        user.setFull_name(userDto.getFull_name());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole("ROLE_" + userDto.getRole().toUpperCase());
        user.setStatus(userDto.getStatus());

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
            return user;
        }

        return null;
    }

    @Override
    public String generateResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null;
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(30);

        PasswordResetToken resetToken = new PasswordResetToken(token, email, expiry);
        tokenRepository.save(resetToken);

        // ✅ Generate Render-based reset URL
        String resetLink = frontendUrl + "/reset-password/" + token;

        // ✅ Send email
        emailService.sendResetEmail(email, resetLink);

        return token;
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);

        if (tokenOptional.isEmpty()) {
            return false;
        }

        PasswordResetToken resetToken = tokenOptional.get();

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        Optional<User> userOptional = userRepository.findByEmail(resetToken.getEmail());
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(resetToken);

        return true;
    }
    
    @Override
    public UserProfileDto getUserProfile(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return null;

        User user = optionalUser.get();
        return new UserProfileDto(
            user.getFull_name(),
            user.getEmail(),
            user.getRole(),
            user.getStatus()
        );
    }

    @Override
    public boolean updateUserProfile(String email, UpdateProfileDto dto) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();

        user.setFull_name(dto.getFull_name());

        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        }

        userRepository.save(user);
        return true;
    }

}
