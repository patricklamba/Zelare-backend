package com.zelare.backend.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromPhoneNumber;

    public void sendSms(String toPhoneNumber, String messageBody) {
        try {
            Twilio.init(accountSid, authToken);

            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(fromPhoneNumber),
                    messageBody
            ).create();

            logger.info("SMS envoyé avec succès. SID: {}", message.getSid());
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi du SMS vers {}: {}", toPhoneNumber, e.getMessage());
            throw new RuntimeException("Échec de l'envoi du SMS", e);
        }
    }

    public void sendOtp(String phoneNumber, String otpCode) {
        String message = String.format(
                "Votre code de vérification Domus est: %s\n\nCe code expire dans 5 minutes.\nNe le partagez avec personne.",
                otpCode
        );
        sendSms(phoneNumber, message);
    }
}
