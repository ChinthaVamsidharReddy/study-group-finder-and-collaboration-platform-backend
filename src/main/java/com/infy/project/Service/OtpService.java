package com.infy.project.Service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class OtpService {
    private Map<String, String> otpMap = new HashMap<>();
    private Map<String, LocalDateTime> expiryMap = new HashMap<>();

    public String generateOtp(String email) {
        String otp = String.valueOf((int)(Math.random()*9000) + 1000); // 4-digit OTP
        otpMap.put(email, otp);
        expiryMap.put(email, LocalDateTime.now().plusMinutes(5));
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        if (!otpMap.containsKey(email)) return false;
        if (LocalDateTime.now().isAfter(expiryMap.get(email))) return false;
        return otpMap.get(email).equals(otp);
    }
}

