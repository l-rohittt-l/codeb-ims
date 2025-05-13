package com.codeb.ims.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateProfileDto {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must be at most 100 characters")
    private String full_name;

    // Optional new password
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String newPassword;

    public UpdateProfileDto() {}

    public UpdateProfileDto(String full_name, String newPassword) {
        this.full_name = full_name;
        this.newPassword = newPassword;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
