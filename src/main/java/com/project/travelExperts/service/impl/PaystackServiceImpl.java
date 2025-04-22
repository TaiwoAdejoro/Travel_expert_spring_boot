package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.dto.response.PaystcakVerifyTransactionResponse;
import com.project.travelExperts.service.PackageService;
import com.project.travelExperts.service.PaystackService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaystackServiceImpl implements PaystackService {

    private static final String PAYSTACK_BASE_URL = "https://api.paystack.co";
    private static final String PAYSTACK_SECRET_KEY = "Bearer SECRET_KEY";

    @Override
    public PaystcakVerifyTransactionResponse verifyTransaction(String reference) {
        String url = PAYSTACK_BASE_URL + "/transaction/verify/" + reference;

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", PAYSTACK_SECRET_KEY);

        // Create the HTTP entity
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Use RestTemplate to make the GET request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PaystcakVerifyTransactionResponse> response = restTemplate.getForEntity(url, PaystcakVerifyTransactionResponse.class, requestEntity);

        // Return the response body
        return response.getBody();
    }
}
