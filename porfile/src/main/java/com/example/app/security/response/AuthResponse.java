package com.example.app.security.response;

import com.example.app.entity.Role;

public record AuthResponse(String accessToken, Role role) {
    private static String tokenType = "Bearer ";
}
