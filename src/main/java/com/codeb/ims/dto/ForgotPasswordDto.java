package com.codeb.ims.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ForgotPasswordDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    public ForgotPasswordDto() {}

    public ForgotPasswordDto(String email) {
        this.email = email;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
