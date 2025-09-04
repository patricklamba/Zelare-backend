package com.zelare.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.zelare.backend.entity.Enums.BookingStatus;
import com.zelare.backend.entity.Enums.ServiceType;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id", nullable = false)
    private User employer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cleaner_id")
    private User cleaner;

    @NotBlank
    @Size(max = 200)
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;

    @Min(1)
    @Max(24)
    @Column(name = "duration_hours", nullable = false)
    private Integer durationHours;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "booking_services", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "service_type")
    private Set<ServiceType> requestedServices;

    @DecimalMin("0.00")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @Size(max = 1000)
    @Column(name = "special_instructions", columnDefinition = "TEXT")
    private String specialInstructions;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Size(max = 500)
    @Column(name = "cancellation_reason")
    private String cancellationReason;

    // Rating et feedback
    @DecimalMin("1.0")
    @DecimalMax("5.0")
    @Digits(integer = 1, fraction = 1)
    @Column(name = "employer_rating", precision = 2, scale = 1)
    private BigDecimal employerRating;

    @Size(max = 1000)
    @Column(name = "employer_feedback", columnDefinition = "TEXT")
    private String employerFeedback;

    @DecimalMin("1.0")
    @DecimalMax("5.0")
    @Digits(integer = 1, fraction = 1)
    @Column(name = "cleaner_rating", precision = 2, scale = 1)
    private BigDecimal cleanerRating;

    @Size(max = 1000)
    @Column(name = "cleaner_feedback", columnDefinition = "TEXT")
    private String cleanerFeedback;

    // Constructeurs
    public Booking() {}

    // Getters et setters
    public User getEmployer() { return employer; }
    public void setEmployer(User employer) { this.employer = employer; }

    public User getCleaner() { return cleaner; }
    public void setCleaner(User cleaner) { this.cleaner = cleaner; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDateTime scheduledDate) { this.scheduledDate = scheduledDate; }

    public Integer getDurationHours() { return durationHours; }
    public void setDurationHours(Integer durationHours) { this.durationHours = durationHours; }

    public Set<ServiceType> getRequestedServices() { return requestedServices; }
    public void setRequestedServices(Set<ServiceType> requestedServices) { this.requestedServices = requestedServices; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }

    public LocalDateTime getAcceptedAt() { return acceptedAt; }
    public void setAcceptedAt(LocalDateTime acceptedAt) { this.acceptedAt = acceptedAt; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public LocalDateTime getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String cancellationReason) { this.cancellationReason = cancellationReason; }

    public BigDecimal getEmployerRating() { return employerRating; }
    public void setEmployerRating(BigDecimal employerRating) { this.employerRating = employerRating; }

    public String getEmployerFeedback() { return employerFeedback; }
    public void setEmployerFeedback(String employerFeedback) { this.employerFeedback = employerFeedback; }

    public BigDecimal getCleanerRating() { return cleanerRating; }
    public void setCleanerRating(BigDecimal cleanerRating) { this.cleanerRating = cleanerRating; }

    public String getCleanerFeedback() { return cleanerFeedback; }
    public void setCleanerFeedback(String cleanerFeedback) { this.cleanerFeedback = cleanerFeedback; }
}
