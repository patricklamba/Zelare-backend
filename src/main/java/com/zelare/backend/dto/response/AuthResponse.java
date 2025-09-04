package com.zelare.backend.dto.response;

import java.time.LocalDateTime;

public class AuthResponse {
    private String token;
    private String refreshToken;
    private UserResponse user;
    private LocalDateTime expiresAt;

    public AuthResponse() {}

    public AuthResponse(String token, String refreshToken, UserResponse user, LocalDateTime expiresAt) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
        this.expiresAt = expiresAt;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public UserResponse getUser() { return user; }
    public void setUser(UserResponse user) { this.user = user; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
}
