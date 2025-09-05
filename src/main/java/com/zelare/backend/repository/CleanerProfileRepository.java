package com.zelare.backend.repository;

import com.zelare.backend.entity.CleanerProfile;
import com.zelare.backend.entity.Enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CleanerProfileRepository extends JpaRepository<CleanerProfile, UUID> {



    List<CleanerProfile> findByIsAvailableTrue();
    @Query("SELECT cp FROM CleanerProfile cp WHERE cp.user.id = :userId")
    Optional<CleanerProfile> findByUserId(@Param("userId") UUID userId);
    @Query("SELECT cp FROM CleanerProfile cp JOIN cp.user u WHERE u.status = 'ACTIVE' AND u.isApproved = true AND cp.isAvailable = true")
    List<CleanerProfile> findAvailableApprovedCleaners();

    @Query("SELECT cp FROM CleanerProfile cp WHERE cp.user.location = :location AND cp.isAvailable = true")
    List<CleanerProfile> findAvailableCleanersInLocation(@Param("location") String location);

    @Query("SELECT cp FROM CleanerProfile cp WHERE cp.hourlyRate BETWEEN :minRate AND :maxRate")
    List<CleanerProfile> findByHourlyRateBetween(@Param("minRate") BigDecimal minRate, @Param("maxRate") BigDecimal maxRate);

    @Query("SELECT cp FROM CleanerProfile cp WHERE cp.rating >= :minRating")
    List<CleanerProfile> findByRatingGreaterThanEqual(@Param("minRating") BigDecimal minRating);

    @Query("SELECT cp FROM CleanerProfile cp JOIN cp.services s WHERE s IN :services")
    List<CleanerProfile> findByServicesIn(@Param("services") Set<ServiceType> services);

    @Query("SELECT cp FROM CleanerProfile cp LEFT JOIN FETCH cp.services WHERE cp.user.id = :userId")
    Optional<CleanerProfile> findByUserIdWithServices(@Param("userId") UUID userId);

    @Query("SELECT cp FROM CleanerProfile cp LEFT JOIN FETCH cp.services WHERE cp.isAvailable = true AND cp.user.isApproved = true")
    List<CleanerProfile> findAvailableApprovedCleanersWithServices();
}
