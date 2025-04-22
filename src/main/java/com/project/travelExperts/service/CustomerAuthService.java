package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.request.LoginRequest;
import com.project.travelExperts.data.dto.request.RegisterCustomerRequest;
import com.project.travelExperts.data.dto.response.AuthResponse;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface CustomerAuthService {
    AuthResponse login(LoginRequest loginRequest) throws AuthException;

    AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    AuthResponse logout(String token);

    AuthResponse register(RegisterCustomerRequest registerCustomerRequest) throws AuthException;

    boolean validateToken(String token);

    String verifySignupToken(String token) throws AuthException;
}
