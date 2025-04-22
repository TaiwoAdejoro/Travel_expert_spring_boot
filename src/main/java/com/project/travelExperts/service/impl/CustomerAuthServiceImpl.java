package com.project.travelExperts.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.travelExperts.config.JwtUtils;
import com.project.travelExperts.data.dto.request.LoginRequest;
import com.project.travelExperts.data.dto.request.RegisterCustomerRequest;
import com.project.travelExperts.data.dto.response.AuthResponse;
import com.project.travelExperts.data.enums.CustomerType;
import com.project.travelExperts.data.enums.Role;
import com.project.travelExperts.data.model.Agent;
import com.project.travelExperts.data.model.Customer;
import com.project.travelExperts.exception.TravelExpertException;
import com.project.travelExperts.service.*;
import com.project.travelExperts.utils.Util;
import com.project.travelExperts.utils.UtilityService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CustomerAuthServiceImpl implements CustomerAuthService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminService adminService;

    @Value("${app.frontend-server-url}")
    private String frontendServerUrl;

    @Override
    public AuthResponse login(LoginRequest loginRequest) throws AuthException {
        Customer customer =  customerService.getCustomerByEmail(loginRequest.getUsername());

        if (customer.getLoginCount() == 5) {
            throw new AuthException("Account locked due to too many failed login attempts");
        }

        if (!customer.isEnabled()){
            throw new AuthException("Account not enabled, please check email for verification link");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())){
            customer.setLoginCount(customer.getLoginCount() + 1);
            checkAndLockCustomerAccount(customer);
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

    @Override
    public AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String refreshToken = authHeader.substring(7);
            String userEmail = this.jwtUtils.extractUsername(refreshToken);
            if (userEmail != null) {
                Customer customer =  customerService.getCustomerByEmail(userEmail);
                if (this.jwtUtils.validateJwtToken(refreshToken)) {
                    String accessToken = jwtUtils.generateJwtToken(customer);
                    AuthResponse authResponse = new AuthResponse(true,"Token generated Successfully!",accessToken,refreshToken);
                    (new ObjectMapper()).writeValue(response.getOutputStream(), authResponse);
                }
            }
        }

        return new AuthResponse(400,false,"Token not generated");

    }

    @Override
    public AuthResponse logout(String token) {
        return null;
    }

    @Override
    public AuthResponse register(RegisterCustomerRequest registerCustomerRequest) throws AuthException {
        checkIfUserExists(registerCustomerRequest.getEmail());
        if (customerService.isCustomerExists(registerCustomerRequest.getEmail())) {
            throw new AuthException("Customer with email " + registerCustomerRequest.getEmail() + " already exists");
        }

        if (null != registerCustomerRequest.getAgentCode() && !registerCustomerRequest.getAgentCode().isEmpty() && !registerCustomerRequest.getAgentCode().isBlank()){
            if (agentService.existsByAgentCode(registerCustomerRequest.getAgentCode())) {
                throw new AuthException("Agent with code " + registerCustomerRequest.getAgentCode() + " does not exist");

            }
            Customer newCustomer  = createNewCustomerFromRequest(registerCustomerRequest);
            newCustomer.setAgent(agentService.findByAgentCode(registerCustomerRequest.getAgentCode()));

            newCustomer = customerService.saveCustomer(newCustomer);

            // Send email to agent

            //Send email to customer
            String verificationToken = jwtUtils.generateJwtToken(newCustomer);
            String verifyUrl = frontendServerUrl + "?token=" + verificationToken;
//            emailService.sendCustomerVerificationMail(newCustomer, verifyUrl);
            return new AuthResponse(201, true,"Customer registered successfully", null);
        }

        Customer newCustomer  = createNewCustomerFromRequest(registerCustomerRequest);

        newCustomer.setAgent(getNextAgent());

        newCustomer = customerService.saveCustomer(newCustomer);

        String verificationToken = jwtUtils.generateJwtToken(newCustomer);
        String verifyUrl = frontendServerUrl + "?token=" + verificationToken;

        // Send email to agent

        // Send email to customer

        return new AuthResponse(201, true,"Customer registered successfully", null);
    }

    private Customer createNewCustomerFromRequest(RegisterCustomerRequest registerCustomerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(registerCustomerRequest.getFirstName());
        customer.setLastName(registerCustomerRequest.getLastName());
        customer.setEmail(registerCustomerRequest.getEmail());
        customer.setRole(Role.CUSTOMER);
        customer.setCustomerType(CustomerType.STANDARD);
        customer.setPassword(passwordEncoder.encode(registerCustomerRequest.getPassword()));
        customer.setCreatedAt(new Date());
        customer.setTotalAmountSpent(BigDecimal.ZERO);
        customer.setEnabled(true);
        return customer;
    }

    private Agent findLastAssignedAgent() {
        Customer lastAssignedCustomer = customerService.findTopBy();
        return lastAssignedCustomer != null ? lastAssignedCustomer.getAgent() : null;
    }

    private Agent getNextAgent() {
        List<Agent> agents = agentService.findAll();
        if (agents.isEmpty()) {
            throw new RuntimeException("No agents available");
        }

        // Assuming agents are sorted by the order they should be assigned
        Agent lastAssignedAgent = findLastAssignedAgent();
        int lastIndex = agents.indexOf(lastAssignedAgent);
        int nextIndex = (lastIndex + 1) % agents.size();
        return agents.get(nextIndex);
    }


    @Override
    public boolean validateToken(String token) {
        return false;
    }

    @Override
    public String verifySignupToken(String token) throws AuthException {
        boolean isTokenValid = jwtUtils.validateJwtToken(token);

        if (!isTokenValid) {
            throw new AuthException(
                    "Cannot verify signup, please request for a new verification or contact support.");
        }

        String email = jwtUtils.getUserNameFromJwtToken(token);

        Customer customer = customerService.getCustomerByEmail(email);

        if (customer.isEnabled()) {
            throw new AuthException("Email already verified");
        }

        customer.setEnabled(true);

        customerService.saveCustomer(customer);

        return token;
    }


    private  void checkAndLockCustomerAccount(Customer customer) {
        if (customer.getLoginCount() == 5) {
            customer.setEnabled(false);
            customer.setLocked(true);
            // Save the updated admin account to the database
            customerService.saveCustomer(customer);
        }
    }

    public void checkIfUserExists(String username) {
        if (customerService.existsByUsername(username)) {
            throw new TravelExpertException("Customer with email: "+username+" already exists");
        } else if (agentService.existsByUsername(username)) {
            throw new TravelExpertException("User with email: "+username+" already exists");
        } else if (adminService.existsByUsername(username)) {
            throw new TravelExpertException("User with email: "+username+" already exists");
        }
    }
}
