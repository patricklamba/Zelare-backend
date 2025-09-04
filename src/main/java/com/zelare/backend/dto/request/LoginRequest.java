package com.zelare.backend.dto.request;

import jakarta.validation.constraints.*;

public class LoginRequest {
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+244\\d{9}$", message = "Invalid Angola phone number format")
    private String phoneNumber;

    public LoginRequest() {}

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
