package com.codeb.ims.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_tokens")
public class OtpToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String otp;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(name = "used", nullable = false)
    private boolean used = false;

    public OtpToken() {}

    // Auto-set expiry to 5 minutes if not already set
    @PrePersist
    public void prePersist() {
        if (this.expiryTime == null) {
            this.expiryTime = LocalDateTime.now().plusMinutes(5);
        }
    }

    // Utility method to check if expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryTime);
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "OtpToken{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", otp='" + otp + '\'' +
                ", expiryTime=" + expiryTime +
                ", used=" + used +
                '}';
    }
}
