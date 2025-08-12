package com.flintinfotech.Moorapan.dto;

public class AuthResponse {
    private String token;
    private Integer userId;  // or Long, depending on your User ID type

    public AuthResponse(String token, Integer userId) {
        this.token = token;
        this.userId = userId;
    }

    // getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}