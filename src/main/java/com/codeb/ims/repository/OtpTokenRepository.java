package com.codeb.ims.repository;

import com.codeb.ims.model.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {

    Optional<OtpToken> findTopByEmailOrderByExpiryTimeDesc(String email);

    Optional<OtpToken> findByEmailAndOtpAndUsedFalse(String email, String otp);
}
