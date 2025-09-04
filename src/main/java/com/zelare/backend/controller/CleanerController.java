package com.zelare.backend.controller;

import com.zelare.backend.dto.response.ApiResponse;
import com.zelare.backend.entity.Enums.UserRole;
import com.zelare.backend.entity.CleanerProfile;
import com.zelare.backend.entity.User;
import com.zelare.backend.repository.CleanerProfileRepository;
import com.zelare.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cleaner")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CleanerController {

    private static final Logger logger = LoggerFactory.getLogger(CleanerController.class);

    @Autowired
    private CleanerProfileRepository cleanerProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<CleanerProfile>>> getAvailableCleaners() {
        try {
            logger.info("Récupération des cleaners disponibles");
            List<CleanerProfile> cleaners = cleanerProfileRepository.findAvailableApprovedCleaners();
            return ResponseEntity.ok(ApiResponse.success(cleaners));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des cleaners: {}", e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("Erreur lors de la récupération"));
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('CLEANER')")
    public ResponseEntity<ApiResponse<CleanerProfile>> getCleanerProfile(Authentication authentication) {
        try {
            String phoneNumber = authentication.getName();
            logger.info("Récupération du profil cleaner pour: {}", phoneNumber);

            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (userOpt.isEmpty() || userOpt.get().getRole() != UserRole.CLEANER) {
                return ResponseEntity.ok(ApiResponse.error("Profil cleaner non trouvé"));
            }

            User user = userOpt.get();
            CleanerProfile profile = user.getCleanerProfile();

            if (profile == null) {
                return ResponseEntity.ok(ApiResponse.error("Profil cleaner non configuré"));
            }

            return ResponseEntity.ok(ApiResponse.success(profile));

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du profil cleaner: {}", e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("Erreur lors de la récupération"));
        }
    }

    @PutMapping("/availability")
    @PreAuthorize("hasAuthority('CLEANER')")
    public ResponseEntity<ApiResponse<String>> updateAvailability(
            @RequestParam Boolean isAvailable,
            Authentication authentication) {
        try {
            String phoneNumber = authentication.getName();
            logger.info("Mise à jour disponibilité pour: {} - {}", phoneNumber, isAvailable);

            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (userOpt.isEmpty() || userOpt.get().getRole() != UserRole.CLEANER) {
                return ResponseEntity.ok(ApiResponse.error("Profil cleaner non trouvé"));
            }

            CleanerProfile profile = userOpt.get().getCleanerProfile();
            if (profile == null) {
                return ResponseEntity.ok(ApiResponse.error("Profil cleaner non configuré"));
            }

            profile.setIsAvailable(isAvailable);
            cleanerProfileRepository.save(profile);

            String message = isAvailable ? "Vous êtes maintenant disponible" : "Vous êtes maintenant indisponible";
            return ResponseEntity.ok(ApiResponse.success(message));

        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de disponibilité: {}", e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("Erreur lors de la mise à jour"));
        }
    }
}

