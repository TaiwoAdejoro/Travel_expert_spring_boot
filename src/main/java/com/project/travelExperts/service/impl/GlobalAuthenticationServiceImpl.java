package com.project.travelExperts.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.travelExperts.config.JwtUtils;
import com.project.travelExperts.data.dto.request.LoginRequest;
import com.project.travelExperts.data.dto.response.AuthResponse;
import com.project.travelExperts.data.model.Admin;
import com.project.travelExperts.data.model.Agent;
import com.project.travelExperts.data.model.Customer;
import com.project.travelExperts.service.AgentService;
import com.project.travelExperts.service.CustomerService;
import com.project.travelExperts.service.GlobalAuthenticationService;
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
public class GlobalAuthenticationServiceImpl implements GlobalAuthenticationService {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest loginRequest) throws AuthException {
        Admin admin =  adminService.findAdminByUsernameForLogin(loginRequest.getUsername());
        if(null != admin){
            if (admin.getLoginCount() == 5) {
                throw new AuthException("Account locked due to too many failed login attempts");
            }

            if (!admin.isEnabled()){
                throw new AuthException("Account has been disabled, please reach out to admin to activate your account");
            }

            if(!passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())){
                admin.setLoginCount(admin.getLoginCount() + 1);
                checkAndLockAdminAccount(admin);
                throw new AuthException("Invalid username or password");
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

        Customer customer =  customerService.getCustomerByEmailForLogin(loginRequest.getUsername());
        if (null != customer){

            if (customer.getLoginCount() == 5) {
                throw new AuthException("Account locked due to too many failed login attempts");
            }

            if (!customer.isEnabled()){
                throw new AuthException("Account is not enabled, please check your email to verify your account");
            }

            if(!passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())){
                customer.setLoginCount(customer.getLoginCount() + 1);
                checkAndLockCustomerAccount(customer);
                throw new AuthException("Username or password is invalid");
            }

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            String refreshToken =  jwtUtils.generateRefreshToken(authentication);


            return new AuthResponse(200, true, "Login successful",Util.mapToCustomerResponseForDetails(customer),jwt, refreshToken);

        }

        Agent agent =  agentService.findByEmailForLogin(loginRequest.getUsername());
        if (null != agent){
            if (agent.getLoginCount() == 5) {
                throw new AuthException("Account locked due to too many failed login attempts");
            }

            if (!agent.isEnabled()){
                throw new AuthException("Account has been disabled, please reach out to your manager to activate your account");
            }

            if(!passwordEncoder.matches(loginRequest.getPassword(), agent.getPassword())){
                agent.setLoginCount(agent.getLoginCount() + 1);
                checkAndLockAgentAccount(agent);
                throw new AuthException("Username or password is invalid");
            }

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            String refreshToken =  jwtUtils.generateRefreshToken(authentication);


            return new AuthResponse(200, true, "Login successful", Util.mapAgentToAgentResponseForDetials(agent),jwt, refreshToken);

        }

        throw new AuthException("Invalid username or password");
    }

    @Override
    public AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String refreshToken = authHeader.substring(7);
            String userEmail = this.jwtUtils.extractUsername(refreshToken);
            Admin admin = adminService.findAdminByUsernameForLogin(userEmail);
            Customer customer = customerService.getCustomerByEmailForLogin(userEmail);
            Agent agent = agentService.findByEmailForLogin(userEmail);

            if (userEmail != null) {
                if (null != admin) {
                    if (this.jwtUtils.validateJwtToken(refreshToken)) {
                        String accessToken = jwtUtils.generateJwtToken(admin);
                        //                        (new ObjectMapper()).writeValue(response.getOutputStream(), authResponse);
                        return new AuthResponse(true, "Token generated Successfully!", accessToken, refreshToken);
                    }
                } else if (null != customer) {
                    if (this.jwtUtils.validateJwtToken(refreshToken)) {
                        String accessToken = jwtUtils.generateJwtToken(agent);
                        //                        (new ObjectMapper()).writeValue(response.getOutputStream(), authResponse);
                        return new AuthResponse(true, "Token generated Successfully!", accessToken, refreshToken);
                    }
                } else if (null != agent) {
                    if (this.jwtUtils.validateJwtToken(refreshToken)) {
                        String accessToken = jwtUtils.generateJwtToken(agent);
                        //                        (new ObjectMapper()).writeValue(response.getOutputStream(), authResponse);
                        return new AuthResponse(true, "Token generated Successfully!", accessToken, refreshToken);
                    }
                }
            }
        }
        return new AuthResponse(400, false, "Token not generated");
    }

    private  void checkAndLockAdminAccount(Admin admin) {
        if (admin.getLoginCount() == 5) {
            admin.setEnabled(false);
            admin.setLocked(true);
            // Save the updated admin account to the database
            adminService.saveAdmin(admin);
        }
    }

    private  void checkAndLockCustomerAccount(Customer customer) {
        if (customer.getLoginCount() == 5) {
            customer.setEnabled(false);
            customer.setLocked(true);
            // Save the updated admin account to the database
            customerService.saveCustomer(customer);
        }
    }

    private  void checkAndLockAgentAccount(Agent agent) {
        if (agent.getLoginCount() == 5) {
            agent.setEnabled(false);
            agent.setLocked(true);
            // Save the updated admin account to the database
            agentService.saveAgent(agent);
        }
    }
}
