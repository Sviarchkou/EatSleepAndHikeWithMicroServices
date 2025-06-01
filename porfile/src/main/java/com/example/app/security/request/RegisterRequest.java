package com.example.app.security.request;

public record RegisterRequest(String username, String email , String password, String confirmNum) {
}
