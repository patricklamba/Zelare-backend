package com.zelare.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Set;
import com.zelare.backend.entity.Enums.UserRole;
import com.zelare.backend.entity.Enums.UserStatus;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotBlank
    @Size(max = 20)
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Email
    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 100)
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.ACTIVE;

    @Size(max = 255)
    @Column(name = "avatar_url")
    private String avatarUrl;

    @Size(max = 200)
    @Column(name = "location")
    private String location;

    @Column(name = "phone_verified", nullable = false)
    private Boolean phoneVerified = false;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

    // Relations
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CleanerProfile cleanerProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EmployerProfile employerProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OtpVerification> otpVerifications;


    public User() {}

    public User(String phoneNumber, String fullName, UserRole role) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.role = role;
    }

    // Getters et setters
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Boolean getPhoneVerified() { return phoneVerified; }
    public void setPhoneVerified(Boolean phoneVerified) { this.phoneVerified = phoneVerified; }

    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean isApproved) { this.isApproved = isApproved; }

    public CleanerProfile getCleanerProfile() { return cleanerProfile; }
    public void setCleanerProfile(CleanerProfile cleanerProfile) { this.cleanerProfile = cleanerProfile; }

    public EmployerProfile getEmployerProfile() { return employerProfile; }
    public void setEmployerProfile(EmployerProfile employerProfile) { this.employerProfile = employerProfile; }

    public Set<OtpVerification> getOtpVerifications() { return otpVerifications; }
    public void setOtpVerifications(Set<OtpVerification> otpVerifications) { this.otpVerifications = otpVerifications; }
}
