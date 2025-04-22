package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.request.LoginRequest;
import com.project.travelExperts.data.dto.response.AuthResponse;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AgentAuthService {
     AuthResponse login(LoginRequest loginRequest) throws AuthException;
     AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    // void logout(String token);
    // void validateToken(String token);
    // String getUsernameFromToken(String token);
    // String generateToken(String username);
    // String generateRefreshToken(String username);
    // String getRefreshTokenFromRequest(HttpServletRequest request);
}
