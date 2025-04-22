package com.project.travelExperts.controller;

import com.project.travelExperts.data.dto.request.LoginRequest;
import com.project.travelExperts.data.dto.request.RegisterCustomerRequest;
import com.project.travelExperts.data.dto.response.AuthResponse;
import com.project.travelExperts.service.CustomerAuthService;
import com.project.travelExperts.service.GlobalAuthenticationService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
//@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class GlobalAuthenticationController {
    @Autowired
    private GlobalAuthenticationService globalAuthenticationService;

    @Autowired
    CustomerAuthService customerAuthService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest loginRequest) throws AuthException {
        return globalAuthenticationService.login(loginRequest);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return  ResponseEntity.ok().body(globalAuthenticationService.refreshToken(request, response));
    }

    @PostMapping("/customer/signup")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterCustomerRequest registerCustomerRequest) throws AuthException {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerAuthService.register(registerCustomerRequest));
    }
    @GetMapping("/verify/{token}")
    public ResponseEntity<AuthResponse> verifySignupToken(@PathVariable String token) throws AuthException {
        String authToken = customerAuthService.verifySignupToken(token);

        AuthResponse response =
                new AuthResponse(200,true,"Token verified successfully",authToken,null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
