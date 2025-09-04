package com.zelare.backend.dto.request;

import jakarta.validation.constraints.*;

public class VerifyOtpRequest {
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+244\\d{9}$", message = "Invalid Angola phone number format")
    private String phoneNumber;

    @NotBlank(message = "OTP code is required")
    @Size(min = 4, max = 6, message = "OTP code must be between 4 and 6 characters")
    private String otpCode;

    public VerifyOtpRequest() {}

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getOtpCode() { return otpCode; }
    public void setOtpCode(String otpCode) { this.otpCode = otpCode; }
}