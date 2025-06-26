package com.codeb.ims.service;

import com.codeb.ims.model.OtpToken;
import com.codeb.ims.repository.OtpTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpTokenRepository otpTokenRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void generateAndSendOtp(String email) {
        String otp = generateOtp();

        OtpToken token = new OtpToken();
        token.setEmail(email);
        token.setOtp(otp);
        token.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        token.setUsed(false);

        otpTokenRepository.save(token);

        // Send email
        String subject = "Your OTP Code";
        String body = "Your OTP is: " + otp + "\nIt will expire in 5 minutes.";
        emailService.sendCustomEmail(email, subject, body);
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        return otpTokenRepository.findByEmailAndOtpAndUsedFalse(email, otp)
                .filter(t -> t.getExpiryTime().isAfter(LocalDateTime.now()))
                .map(t -> {
                    t.setUsed(true);
                    otpTokenRepository.save(t);
                    return true;
                })
                .orElse(false);
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
