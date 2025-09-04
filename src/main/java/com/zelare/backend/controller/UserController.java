package com.zelare.backend.controller;

import com.zelare.backend.dto.request.UpdateProfileRequest;
import com.zelare.backend.dto.response.ApiResponse;
import com.zelare.backend.dto.response.UserResponse;
import com.zelare.backend.entity.User;
import com.zelare.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUserProfile(Authentication authentication) {
        try {
            String phoneNumber = authentication.getName();
            logger.info("Récupération du profil pour: {}", phoneNumber);

            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (userOpt.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.error("Utilisateur non trouvé"));
            }

            User user = userOpt.get();
            UserResponse userResponse = mapToUserResponse(user);

            return ResponseEntity.ok(ApiResponse.success(userResponse));

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du profil: {}", e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("Erreur lors de la récupération du profil"));
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @RequestBody UpdateProfileRequest request,
            Authentication authentication) {
        try {
            String phoneNumber = authentication.getName();
            logger.info("Mise à jour du profil pour: {}", phoneNumber);

            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (userOpt.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.error("Utilisateur non trouvé"));
            }

            User user = userOpt.get();

            // Mettre à jour les champs
            if (request.getFullName() != null) {
                user.setFullName(request.getFullName());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            if (request.getLocation() != null) {
                user.setLocation(request.getLocation());
            }
            if (request.getAvatarUrl() != null) {
                user.setAvatarUrl(request.getAvatarUrl());
            }

            userRepository.save(user);
            UserResponse userResponse = mapToUserResponse(user);

            return ResponseEntity.ok(ApiResponse.success("Profil mis à jour", userResponse));

        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour du profil: {}", e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("Erreur lors de la mise à jour"));
        }
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
