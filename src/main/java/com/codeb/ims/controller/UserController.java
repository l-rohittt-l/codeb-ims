package com.codeb.ims.controller;

import com.codeb.ims.dto.LoginRequestDto;
import com.codeb.ims.dto.ResetPasswordDto;
import com.codeb.ims.dto.UserRegistrationDto;
import com.codeb.ims.model.User;
import com.codeb.ims.service.EmailService;
import com.codeb.ims.service.UserService;
import jakarta.validation.Valid;

import java.util.HashMap;
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {
            Map<String, String> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("role", user.getRole());  // ✅ THIS IS CRUCIAL
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
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

        String resetUrl = "http://localhost:8080/api/reset-password/" + token;
        emailService.sendResetEmail(dto.getEmail(), resetUrl);

        return ResponseEntity.ok("Reset link has been sent to your email.");
    }
    
    @PatchMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @Valid @RequestBody ResetPasswordDto dto) {
        boolean result = userService.resetPassword(token, dto.getNewPassword());

        if (!result) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        return ResponseEntity.ok("Password reset successfully. You can now log in with your new password.");
    }

}
