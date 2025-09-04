package com.zelare.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import com.zelare.backend.entity.Enums.VipLevel;
import com.zelare.backend.entity.Enums.PaymentMethod;

@Entity
@Table(name = "employer_profiles")
public class EmployerProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "vip_level")
    private VipLevel vipLevel = VipLevel.STANDARD;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_payment_method")
    private PaymentMethod preferredPaymentMethod;

    @Size(max = 1000)
    @Column(name = "preferences", columnDefinition = "TEXT")
    private String preferences;

    @DecimalMin("0.00")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "credit_balance", precision = 10, scale = 2, nullable = false)
    private BigDecimal creditBalance = BigDecimal.ZERO;

    @Min(0)
    @Column(name = "total_bookings", nullable = false)
    private Integer totalBookings = 0;

    @Column(name = "verified_identity", nullable = false)
    private Boolean verifiedIdentity = false;


    public EmployerProfile() {}

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public VipLevel getVipLevel() { return vipLevel; }
    public void setVipLevel(VipLevel vipLevel) { this.vipLevel = vipLevel; }

    public PaymentMethod getPreferredPaymentMethod() { return preferredPaymentMethod; }
    public void setPreferredPaymentMethod(PaymentMethod preferredPaymentMethod) {
        this.preferredPaymentMethod = preferredPaymentMethod;
    }

    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }

    public BigDecimal getCreditBalance() { return creditBalance; }
    public void setCreditBalance(BigDecimal creditBalance) { this.creditBalance = creditBalance; }

    public Integer getTotalBookings() { return totalBookings; }
    public void setTotalBookings(Integer totalBookings) { this.totalBookings = totalBookings; }

    public Boolean getVerifiedIdentity() { return verifiedIdentity; }
    public void setVerifiedIdentity(Boolean verifiedIdentity) { this.verifiedIdentity = verifiedIdentity; }
}

