package com.zelare.backend.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import com.zelare.backend.entity.Enums.ServiceType;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cleaner_profiles")
public class CleanerProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Min(18)
    @Max(70)
    @Column(name = "age")
    private Integer age;

    @Min(0)
    @Column(name = "experience_years")
    private Integer experienceYears;

    @DecimalMin("0.00")
    @Digits(integer = 6, fraction = 2)
    @Column(name = "hourly_rate", precision = 8, scale = 2)
    private BigDecimal hourlyRate;

    @Size(max = 1000)
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "cleaner_services", joinColumns = @JoinColumn(name = "cleaner_profile_id"))
    @Column(name = "service_type")
    private Set<ServiceType> services;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    @Digits(integer = 1, fraction = 2)
    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating = BigDecimal.ZERO;

    @Min(0)
    @Column(name = "total_jobs", nullable = false)
    private Integer totalJobs = 0;

    @Column(name = "background_check_verified", nullable = false)
    private Boolean backgroundCheckVerified = false;

    // Constructeurs, getters et setters
    public CleanerProfile() {}
    @JsonProperty("userId")
    public UUID getUserId() {
        return user != null ? user.getId() : null;
    }
    // Tous les getters et setters...
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public Set<ServiceType> getServices() { return services; }
    public void setServices(Set<ServiceType> services) { this.services = services; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public Integer getTotalJobs() { return totalJobs; }
    public void setTotalJobs(Integer totalJobs) { this.totalJobs = totalJobs; }

    public Boolean getBackgroundCheckVerified() { return backgroundCheckVerified; }
    public void setBackgroundCheckVerified(Boolean backgroundCheckVerified) { this.backgroundCheckVerified = backgroundCheckVerified; }
}
