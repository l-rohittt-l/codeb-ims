package com.codeb.ims.controller;

import java.security.Principal;
import com.codeb.ims.dto.UserProfileDto;
import com.codeb.ims.dto.UpdateProfileDto;


import com.codeb.ims.dto.LoginRequestDto;
import com.codeb.ims.dto.LoginResponseDto;
import com.codeb.ims.dto.ResetPasswordDto;
import com.codeb.ims.dto.UserRegistrationDto;
import com.codeb.ims.model.User;
import com.codeb.ims.security.JwtUtil;
import com.codeb.ims.service.EmailService;
import com.codeb.ims.service.UserService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codeb.ims.dto.ForgotPasswordDto;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow frontend requests
public class UserController {

	
    private final UserService userService;
    
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtUtil jwtUtil;

    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto userDto) {
        boolean registered = userService.registerUser(userDto);

        if (registered) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.badRequest().body("Email already exists");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto) {
        User user = userService.login(loginDto.getEmail(), loginDto.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        String role = user.getRole().replace("ROLE_", "");

        return ResponseEntity.ok(new LoginResponseDto(token, role));
    }



    
    @GetMapping("/admin/dashboard")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Welcome to Admin Dashboard");
    }

    @GetMapping("/sales/dashboard")
    public ResponseEntity<String> salesDashboard() {
        return ResponseEntity.ok("Welcome to Sales Dashboard");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordDto dto) {
        String token = userService.generateResetToken(dto.getEmail());

        if (token == null) {
            return ResponseEntity.badRequest().body("Email not found");
        }

        return ResponseEntity.ok("Reset link has been sent to your email.");
    }

    
    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @Valid @RequestBody ResetPasswordDto dto) {
        boolean result = userService.resetPassword(token, dto.getNewPassword());

        if (!result) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        return ResponseEntity.ok("Password reset successfully. You can now log in with your new password.");
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Principal principal) {
    	System.out.println("Logged in as: " + principal.getName());

        String email = principal.getName(); // Spring Security pulls it from session

        UserProfileDto profile = userService.getUserProfile(email);
        if (profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok(profile);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(Principal principal, @Valid @RequestBody UpdateProfileDto dto) {
        String email = principal.getName();
        boolean updated = userService.updateUserProfile(email, dto);

        if (updated) {
            return ResponseEntity.ok("Profile updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/admin/promote/{userId}")
    public ResponseEntity<?> promoteUserToAdmin(@PathVariable Long userId) {
        boolean updated = userService.promoteUserToAdmin(userId);
        if (updated) {
            return ResponseEntity.ok("User promoted to ADMIN successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @GetMapping("/admin/sales-users")
    public ResponseEntity<?> getSalesUsers() {
        List<User> salesUsers = userService.getSalesUsers();
        return ResponseEntity.ok(salesUsers);
    }

    @PutMapping("/admin/demote/{userId}")
    public ResponseEntity<?> demoteUser(@PathVariable Long userId) {
        boolean success = userService.changeUserRole(userId, "ROLE_SALES");

        if (success) {
            return ResponseEntity.ok("User demoted to SALES");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or already SALES");
        }
    }
    @GetMapping("/admin/admin-users")
    public ResponseEntity<?> getAllAdmins() {
        List<User> admins = userService.getUsersByRole("ROLE_ADMIN");
        return ResponseEntity.ok(admins);
    }

}
