package com.codeb.ims.controller;

import com.codeb.ims.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@CrossOrigin(origins = "*")
public class OtpController {

    @Autowired
    private OtpService otpService;

    // 🔹 Send OTP to email
    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        otpService.generateAndSendOtp(email);
        return ResponseEntity.ok("OTP sent to " + email);
    }

    // 🔹 Verify OTP
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.verifyOtp(email, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }
    }
}
