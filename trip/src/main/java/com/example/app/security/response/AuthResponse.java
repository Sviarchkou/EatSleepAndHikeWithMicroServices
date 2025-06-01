package com.example.app.security.response;

public record AuthResponse(String accessToken) {
    private static String tokenType = "Bearer ";
}
