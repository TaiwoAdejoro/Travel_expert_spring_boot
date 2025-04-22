package com.project.travelExperts.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.travelExperts.config.JwtUtils;
import com.project.travelExperts.data.dto.response.AuthResponse;
import com.project.travelExperts.data.dto.request.LoginRequest;
import com.project.travelExperts.data.model.Admin;
import com.project.travelExperts.service.AdminAuthService;
import com.project.travelExperts.service.AdminService;
import com.project.travelExperts.utils.Util;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    @Autowired
    private  AdminService adminService;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private  JwtUtils jwtUtils;

    @Override
    public AuthResponse login(LoginRequest loginRequest) throws AuthException {
        Admin admin =  adminService.findAdminByUsername(loginRequest.getUsername());

        if (admin.getLoginCount() == 5) {
            throw new AuthException("Account locked due to too many failed login attempts");
        }

        if (!admin.isEnabled()){
            throw new AuthException("Account not enabled, please check email for verification link");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())){
            admin.setLoginCount(admin.getLoginCount() + 1);
            checkAndLockAdminAccount(admin);
        }

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshToken =  jwtUtils.generateRefreshToken(authentication);


        return new AuthResponse(200, true, "Login successful", Util.mapToAdminResponseForDetails(admin),jwt, refreshToken);
    }

    @Override
    public AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String refreshToken = authHeader.substring(7);
            String userEmail = this.jwtUtils.extractUsername(refreshToken);
            if (userEmail != null) {
                Admin user =  adminService.findAdminByUsername(userEmail);
                if (this.jwtUtils.validateJwtToken(refreshToken)) {
                    String accessToken = jwtUtils.generateJwtToken(user);
                    AuthResponse authResponse = new AuthResponse(true,"Token generated Successfully!",accessToken,refreshToken);
                    (new ObjectMapper()).writeValue(response.getOutputStream(), authResponse);
                }
            }
        }

        return new AuthResponse(400,false,"Token not generated");
    }

    private  void checkAndLockAdminAccount(Admin admin) {
        if (admin.getLoginCount() == 5) {
            admin.setEnabled(false);
            admin.setLocked(true);
            // Save the updated admin account to the database
            adminService.saveAdmin(admin);
        }
    }

}
