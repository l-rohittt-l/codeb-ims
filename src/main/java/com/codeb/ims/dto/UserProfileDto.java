package com.codeb.ims.dto;

public class UserProfileDto {
    private String full_name;
    private String email;
    private String role;
    private String status;

    public UserProfileDto() {}

    public UserProfileDto(String full_name, String email, String role, String status) {
        this.full_name = full_name;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
