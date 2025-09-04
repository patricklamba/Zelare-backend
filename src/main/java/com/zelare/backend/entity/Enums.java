// src/main/java/com/zelare/backend/entity/Enums.java
package com.zelare.backend.entity;

/**
 * Consolidated enums for Zelare backend application
 * Contains all enumeration types used across the application
 */
public final class Enums {

    // Prevent instantiation
    private Enums() {}

    /**
     * User roles in the system
     */
    public enum UserRole {
        CLEANER, EMPLOYER, ADMIN
    }

    /**
     * User account status
     */
    public enum UserStatus {
        ACTIVE, INACTIVE, SUSPENDED, PENDING_APPROVAL
    }

    /**
     * VIP levels for employer accounts
     */
    public enum VipLevel {
        STANDARD, GOLD, PLATINUM, DIAMOND
    }

    /**
     * Available payment methods
     */
    public enum PaymentMethod {
        CASH, BANK_TRANSFER, MOBILE_MONEY, CREDIT_CARD
    }

    /**
     * Types of cleaning services offered
     */
    public enum ServiceType {
        HOUSE_CLEANING,
        DEEP_CLEANING,
        OFFICE_CLEANING,
        POST_CONSTRUCTION,
        WINDOW_CLEANING,
        CARPET_CLEANING,
        LAUNDRY,
        IRONING,
        ORGANIZING
    }

    /**
     * Booking lifecycle status
     */
    public enum BookingStatus {
        PENDING,
        ACCEPTED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED,
        REJECTED
    }

    /**
     * OTP verification types
     */
    public enum OtpType {
        LOGIN,
        REGISTRATION,
        PASSWORD_RESET
    }
}