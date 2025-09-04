package com.zelare.backend.repository;

import com.zelare.backend.entity.Enums.UserStatus;
import com.zelare.backend.entity.Enums.UserRole;
import com.zelare.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    List<User> findByRole(UserRole role);

    List<User> findByRoleAndStatus(UserRole role, UserStatus status);

    @Query("SELECT u FROM User u WHERE u.role = :role AND u.isApproved = true AND u.status = 'ACTIVE'")
    List<User> findActiveApprovedUsersByRole(@Param("role") UserRole role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    Long countByRole(@Param("role") UserRole role);
}
