package com.codeb.ims.controller;

import com.codeb.ims.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-jwt")
public class JwtTestController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/generate")
    public String generateToken(@RequestParam String email) {
        return jwtUtil.generateToken(email);
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam String token) {
        return jwtUtil.validateToken(token);
    }

    @GetMapping("/extract")
    public String extractUsername(@RequestParam String token) {
        return jwtUtil.extractUsername(token);
    }
}
