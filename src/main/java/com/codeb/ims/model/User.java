package com.codeb.ims.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(nullable = false, length = 100)
    private String full_name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private String role; // Will store 'ADMIN' or 'SALES'

    @Column(nullable = false, columnDefinition = "ENUM('active','inactive') default 'active'")
    private String status = "active";

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt = Timestamp.from(Instant.now());

    // Constructors
    public User() {}

    public User(String full_name, String email, String password, String role, String status) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdAt = Timestamp.from(Instant.now());
    }

    // Getters and Setters
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
