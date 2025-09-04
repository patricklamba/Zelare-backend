package com.zelare.backend.dto.request;

import jakarta.validation.constraints.*;
import com.zelare.backend.entity.Enums.UserRole;

public class RegisterRequest {
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+244\\d{9}$", message = "Invalid Angola phone number format")
    private String phoneNumber;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotNull(message = "Role is required")
    private UserRole role;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    public RegisterRequest() {}

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
