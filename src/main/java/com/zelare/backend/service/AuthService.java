package com.zelare.backend.service;


import com.zelare.backend.dto.request.LoginRequest;
import com.zelare.backend.dto.request.RegisterRequest;
import com.zelare.backend.dto.request.VerifyOtpRequest;
import com.zelare.backend.dto.response.ApiResponse;
import com.zelare.backend.dto.response.AuthResponse;
import com.zelare.backend.dto.response.UserResponse;
import com.zelare.backend.entity.*;
import com.zelare.backend.repository.OtpRepository;
import com.zelare.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import com.zelare.backend.entity.Enums.OtpType;
import com.zelare.backend.entity.Enums.UserRole;
import com.zelare.backend.entity.Enums.UserStatus;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final SecureRandom random = new SecureRandom();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private SmsService smsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Value("${otp.length}")
    private int otpLength;

    @Value("${otp.expiration-minutes}")
    private int otpExpirationMinutes;

    @Value("${otp.max-attempts}")
    private int maxOtpAttempts;

    @Transactional
    public ApiResponse<String> sendOtp(LoginRequest request) {
        try {
            String phoneNumber = request.getPhoneNumber();
            logger.info("Demande d'envoi OTP pour: {}", phoneNumber);

            // Vérifier si l'utilisateur existe
            boolean userExists = userRepository.existsByPhoneNumber(phoneNumber);

            // Invalider les OTP précédents non vérifiés
            invalidatePreviousOtps(phoneNumber);

            // Générer un nouveau code OTP
            String otpCode = generateOtpCode();
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(otpExpirationMinutes);

            // Sauvegarder l'OTP
            OtpVerification otp = new OtpVerification(
                    phoneNumber,
                    otpCode,
                    userExists ? OtpType.LOGIN : OtpType.REGISTRATION,
                    expiresAt
            );
            otpRepository.save(otp);

            // Envoyer le SMS
            smsService.sendOtp(phoneNumber, otpCode);

            logger.info("OTP envoyé avec succès pour: {}", phoneNumber);
            return ApiResponse.success("Code de vérification envoyé");

        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi OTP: {}", e.getMessage());
            return ApiResponse.error("Erreur lors de l'envoi du code de vérification");
        }
    }

    @Transactional
    public ApiResponse<AuthResponse> verifyOtp(VerifyOtpRequest request) {
        try {
            String phoneNumber = request.getPhoneNumber();
            String otpCode = request.getOtpCode();

            logger.info("Vérification OTP pour: {}", phoneNumber);

            // Trouver l'OTP valide
            Optional<OtpVerification> otpOpt = otpRepository
                    .findByPhoneNumberAndOtpCodeAndVerifiedFalse(phoneNumber, otpCode);

            if (otpOpt.isEmpty()) {
                return ApiResponse.error("Code de vérification invalide");
            }

            OtpVerification otp = otpOpt.get();

            // Vérifier l'expiration
            if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
                return ApiResponse.error("Code de vérification expiré");
            }

            // Vérifier le nombre de tentatives
            if (otp.getAttempts() >= maxOtpAttempts) {
                return ApiResponse.error("Nombre maximum de tentatives dépassé");
            }

            // Incrémenter les tentatives
            otp.setAttempts(otp.getAttempts() + 1);

            // Marquer l'OTP comme vérifié
            otp.setVerified(true);
            otp.setVerifiedAt(LocalDateTime.now());
            otpRepository.save(otp);

            // Trouver ou créer l'utilisateur
            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            User user;

            if (userOpt.isPresent()) {
                // Utilisateur existant - connexion
                user = userOpt.get();
                user.setPhoneVerified(true);
            } else {
                // Pour l'inscription, l'utilisateur devrait déjà être créé
                return ApiResponse.error("Utilisateur non trouvé. Veuillez vous inscrire d'abord.");
            }

            userRepository.save(user);

            // Créer les tokens JWT
            UserDetails userDetails = userService.loadUserByUsername(phoneNumber);
            String accessToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            // Créer la réponse
            UserResponse userResponse = mapToUserResponse(user);
            AuthResponse authResponse = new AuthResponse(
                    accessToken,
                    refreshToken,
                    userResponse,
                    LocalDateTime.now().plusSeconds(86400) // 24h
            );

            logger.info("Connexion réussie pour: {}", phoneNumber);
            return ApiResponse.success("Connexion réussie", authResponse);

        } catch (Exception e) {
            logger.error("Erreur lors de la vérification OTP: {}", e.getMessage());
            return ApiResponse.error("Erreur lors de la vérification");
        }
    }

    @Transactional
    public ApiResponse<String> register(RegisterRequest request) {
        try {
            String phoneNumber = request.getPhoneNumber();

            logger.info("Inscription pour: {}", phoneNumber);

            // Vérifier si l'utilisateur existe déjà
            if (userRepository.existsByPhoneNumber(phoneNumber)) {
                return ApiResponse.error("Un compte existe déjà avec ce numéro");
            }

            // Créer l'utilisateur
            User user = new User();
            user.setPhoneNumber(phoneNumber);
            user.setFullName(request.getFullName());
            user.setRole(request.getRole());
            user.setEmail(request.getEmail());
            user.setLocation(request.getLocation());
            user.setPhoneVerified(false);
            user.setIsApproved(false);
            user.setStatus(UserStatus.PENDING_APPROVAL);

            userRepository.save(user);

            // Créer le profil spécifique selon le rôle
            if (request.getRole() == UserRole.CLEANER) {
                CleanerProfile cleanerProfile = new CleanerProfile();
                cleanerProfile.setUser(user);
                user.setCleanerProfile(cleanerProfile);
            } else if (request.getRole() == UserRole.EMPLOYER) {
                EmployerProfile employerProfile = new EmployerProfile();
                employerProfile.setUser(user);
                user.setEmployerProfile(employerProfile);
            }

            userRepository.save(user);

            // Envoyer l'OTP d'inscription
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setPhoneNumber(phoneNumber);
            return sendOtp(loginRequest);

        } catch (Exception e) {
            logger.error("Erreur lors de l'inscription: {}", e.getMessage());
            return ApiResponse.error("Erreur lors de l'inscription");
        }
    }

    private void invalidatePreviousOtps(String phoneNumber) {
        Optional<OtpVerification> previousOtp = otpRepository
                .findTopByPhoneNumberAndVerifiedFalseOrderByCreatedAtDesc(phoneNumber);

        if (previousOtp.isPresent()) {
            OtpVerification otp = previousOtp.get();
            otp.setVerified(true); // Marquer comme "utilisé" pour l'invalider
            otpRepository.save(otp);
        }
    }

    private String generateOtpCode() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setRole(user.getRole());
        response.setAvatarUrl(user.getAvatarUrl());
        response.setLocation(user.getLocation());
        response.setPhoneVerified(user.getPhoneVerified());
        response.setIsApproved(user.getIsApproved());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}

