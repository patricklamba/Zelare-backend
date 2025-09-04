package com.zelare.backend.controller;

import com.zelare.backend.dto.request.LoginRequest;
import com.zelare.backend.dto.request.RegisterRequest;
import com.zelare.backend.dto.request.VerifyOtpRequest;
import com.zelare.backend.dto.response.ApiResponse;
import com.zelare.backend.dto.response.AuthResponse;
import com.zelare.backend.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<String>> sendOtp(@Valid @RequestBody LoginRequest request) {
        logger.info("Demande d'envoi OTP reçue pour: {}", request.getPhoneNumber());
        ApiResponse<String> response = authService.sendOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        logger.info("Demande de vérification OTP reçue pour: {}", request.getPhoneNumber());
        ApiResponse<AuthResponse> response = authService.verifyOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Demande d'inscription reçue pour: {} - Rôle: {}",
                request.getPhoneNumber(), request.getRole());
        ApiResponse<String> response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        // Pour l'instant, simple réponse car JWT est stateless
        // Dans une implémentation complète, on pourrait blacklister le token
        logger.info("Demande de déconnexion reçue");
        return ResponseEntity.ok(ApiResponse.success("Déconnexion réussie"));
    }
}

