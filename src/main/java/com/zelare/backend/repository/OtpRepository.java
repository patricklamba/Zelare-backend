package com.zelare.backend.repository;

import com.zelare.backend.entity.Enums.OtpType;
import com.zelare.backend.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRepository extends JpaRepository<OtpVerification, UUID> {

    Optional<OtpVerification> findTopByPhoneNumberAndVerifiedFalseOrderByCreatedAtDesc(String phoneNumber);

    Optional<OtpVerification> findByPhoneNumberAndOtpCodeAndVerifiedFalse(String phoneNumber, String otpCode);

    List<OtpVerification> findByPhoneNumberAndOtpType(String phoneNumber, OtpType otpType);

    @Query("SELECT o FROM OtpVerification o WHERE o.phoneNumber = :phoneNumber AND o.verified = false AND o.expiresAt > :currentTime")
    List<OtpVerification> findValidOtpsByPhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("currentTime") LocalDateTime currentTime);

    @Modifying
    @Transactional
    @Query("DELETE FROM OtpVerification o WHERE o.expiresAt < :expiryTime")
    void deleteExpiredOtps(@Param("expiryTime") LocalDateTime expiryTime);

    @Modifying
    @Transactional
    @Query("UPDATE OtpVerification o SET o.verified = true, o.verifiedAt = :verifiedAt WHERE o.id = :id")
    void markAsVerified(@Param("id") UUID id, @Param("verifiedAt") LocalDateTime verifiedAt);
}