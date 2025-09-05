package com.zelare.backend.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CleanerProfileResponse {
    private UUID id;
    private UUID userId;
    private Integer age;
    private Integer experienceYears;
    private BigDecimal hourlyRate;
    private String bio;
    private List<String> services;
    private Boolean isAvailable;
    private BigDecimal rating;
    private Integer totalJobs;
    private Boolean backgroundCheckVerified;

    // Constructors
    public CleanerProfileResponse() {}

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(Integer totalJobs) {
        this.totalJobs = totalJobs;
    }

    public Boolean getBackgroundCheckVerified() {
        return backgroundCheckVerified;
    }

    public void setBackgroundCheckVerified(Boolean backgroundCheckVerified) {
        this.backgroundCheckVerified = backgroundCheckVerified;
    }
}