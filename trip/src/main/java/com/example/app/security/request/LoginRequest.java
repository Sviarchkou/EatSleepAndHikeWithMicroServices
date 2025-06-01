package com.example.app.security.request;

public record LoginRequest(String usernameOrEmail, String password) {
}
