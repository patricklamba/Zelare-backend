package com.zelare.backend.repository;

import com.zelare.backend.entity.EmployerProfile;
import com.zelare.backend.entity.Enums.VipLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, UUID> {

    Optional<EmployerProfile> findByUserId(UUID userId);

    List<EmployerProfile> findByVipLevel(VipLevel vipLevel);

    @Query("SELECT ep FROM EmployerProfile ep WHERE ep.creditBalance >= :minBalance")
    List<EmployerProfile> findByCreditBalanceGreaterThanEqual(@Param("minBalance") BigDecimal minBalance);

    @Query("SELECT COUNT(ep) FROM EmployerProfile ep WHERE ep.vipLevel = :vipLevel")
    Long countByVipLevel(@Param("vipLevel") VipLevel vipLevel);
}
