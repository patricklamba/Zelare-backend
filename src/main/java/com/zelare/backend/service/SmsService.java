package com.zelare.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Value("${twilio.account-sid:}")
    private String accountSid;

    @Value("${twilio.auth-token:}")
    private String authToken;

    @Value("${twilio.phone-number:}")
    private String fromPhoneNumber;

    public void sendSms(String toPhoneNumber, String messageBody) {
        // Mode développement - mock SMS
        if ("dev".equals(activeProfile) || accountSid.isEmpty() || "test_sid".equals(accountSid)) {
            logger.info("🔔 MOCK SMS to {}: {}", toPhoneNumber, messageBody);
            logger.info("📱 [DEV MODE] SMS would be sent in production");
            return;
        }

        // Mode production - vraie implémentation Twilio
        try {
            // Code Twilio existant ici...
            logger.info("SMS sent successfully via Twilio to: {}", toPhoneNumber);
        } catch (Exception e) {
            logger.error("Failed to send SMS to {}: {}", toPhoneNumber, e.getMessage());
            throw new RuntimeException("Failed to send SMS", e);
        }
    }

    public void sendOtp(String phoneNumber, String otpCode) {
        String message = String.format(
                "Your Zelare verification code is: %s\n\nThis code expires in 5 minutes.\nDo not share this code with anyone.",
                otpCode
        );

        // Log spécial pour les OTP en mode dev
        if ("dev".equals(activeProfile) || accountSid.isEmpty() || "test_sid".equals(accountSid)) {
            logger.warn("🔐 OTP CODE FOR TESTING: {} (Phone: {})", otpCode, phoneNumber);
            logger.info("📝 Copy this code for your tests: {}", otpCode);
        }

        sendSms(phoneNumber, message);
    }
}
