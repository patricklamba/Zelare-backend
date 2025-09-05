package com.zelare.backend.controller;

import com.zelare.backend.dto.response.ApiResponse;
import com.zelare.backend.dto.response.CleanerProfileResponse;
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
import java.util.stream.Collectors;

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
    public ResponseEntity<ApiResponse<List<CleanerProfileResponse>>> getAvailableCleaners() {
        try {
            logger.info("Récupération des cleaners disponibles");
            List<CleanerProfile> cleaners = cleanerProfileRepository.findAvailableApprovedCleanersWithServices();

            // Convert to DTOs
            List<CleanerProfileResponse> responses = cleaners.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des cleaners: {}", e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("Erreur lors de la récupération"));
        }
    }

    // Helper method to convert CleanerProfile to DTO
    private CleanerProfileResponse convertToDTO(CleanerProfile profile) {
        CleanerProfileResponse response = new CleanerProfileResponse();
        response.setId(profile.getId());
        response.setUserId(profile.getUser().getId());
        response.setAge(profile.getAge());
        response.setExperienceYears(profile.getExperienceYears());
        response.setHourlyRate(profile.getHourlyRate());
        response.setBio(profile.getBio());

        if (profile.getServices() != null) {
            List<String> servicesList = profile.getServices().stream()
                    .map(serviceType -> serviceType.name())
                    .collect(Collectors.toList());
            response.setServices(servicesList);
        }

        response.setIsAvailable(profile.getIsAvailable());
        response.setRating(profile.getRating());
        response.setTotalJobs(profile.getTotalJobs());
        response.setBackgroundCheckVerified(profile.getBackgroundCheckVerified());

        return response;
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('CLEANER')")
    public ResponseEntity<ApiResponse<CleanerProfileResponse>> getCleanerProfile(Authentication authentication) {
        try {
            String phoneNumber = authentication.getName();
            logger.info("Récupération du profil cleaner pour: {}", phoneNumber);

            Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);
            if (userOpt.isEmpty() || userOpt.get().getRole() != UserRole.CLEANER) {
                return ResponseEntity.ok(ApiResponse.error("Profil cleaner non trouvé"));
            }

            User user = userOpt.get();
            CleanerProfile profile = cleanerProfileRepository.findByUserIdWithServices(user.getId())
                    .orElse(null);

            if (profile == null) {
                return ResponseEntity.ok(ApiResponse.error("Profil cleaner non configuré"));
            }

            // Convert to DTO
            CleanerProfileResponse response = new CleanerProfileResponse();
            response.setId(profile.getId());
            response.setUserId(user.getId());
            response.setAge(profile.getAge());
            response.setExperienceYears(profile.getExperienceYears());
            response.setHourlyRate(profile.getHourlyRate());
            response.setBio(profile.getBio());

            // Convert Set<ServiceType> to List<String>
            if (profile.getServices() != null) {
                List<String> servicesList = profile.getServices().stream()
                        .map(serviceType -> serviceType.name())
                        .collect(java.util.stream.Collectors.toList());
                response.setServices(servicesList);
            }

            response.setIsAvailable(profile.getIsAvailable());
            response.setRating(profile.getRating());
            response.setTotalJobs(profile.getTotalJobs());
            response.setBackgroundCheckVerified(profile.getBackgroundCheckVerified());

            return ResponseEntity.ok(ApiResponse.success(response));

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


            String message = isAvailable ? "Vous êtes maintenant disponible" : "Vous êtes maintenant indisponible";
            return ResponseEntity.ok(ApiResponse.success(message));

        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de disponibilité: {}", e.getMessage());
            return ResponseEntity.ok(ApiResponse.error("Erreur lors de la mise à jour"));
        }
    }
}

