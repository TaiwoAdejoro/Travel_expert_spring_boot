package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.response.AuthResponse;
import com.project.travelExperts.data.dto.request.LoginRequest;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AdminAuthService {
    AuthResponse login(LoginRequest loginRequest) throws AuthException;

    AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
