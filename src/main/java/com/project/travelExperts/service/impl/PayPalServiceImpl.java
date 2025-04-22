package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.dto.response.PayPalCaptureOrderResponse;
import com.project.travelExperts.exception.TravelExpertException;
import com.project.travelExperts.service.PayPalService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayPalServiceImpl  implements PayPalService {

    private final String clientId = "AV5W8Lo6Vc0K1u_exj73ropRSvukrSYccLZshtLb5kIP9MOq2Qb4TRX1JVB9JOunVcGefVR9Yml0ZJue";
    private final String clientSecret = "ELq9ZiReQPl5BpjeC7cpadjSVo7SBWiGxL4-aZTIBhGZ5i7vhBbYCBAHpbKfpkhDZu3CSaPgnUAGo6Oy";
    private final String baseUrl = "https://api-m.sandbox.paypal.com"; // Use "https://api-m.paypal.com" for production

    @Override
    public String getAccessToken() {
        String url = baseUrl + "/v1/oauth2/token";
        RestTemplate restTemplate = new RestTemplate();

        // Encode clientId and clientSecret
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return (String) response.getBody().get("access_token");
        }
        throw new TravelExpertException("Failed to retrieve access token");

    }

    @Override
    public Map createOrder(double total, String currency) {
        String url = baseUrl + "/v2/checkout/orders";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAccessToken());

        Map<String, Object> amount = new HashMap<>();
        amount.put("currency_code", currency);
        amount.put("value", String.format("%.2f", total));

        Map<String, Object> purchaseUnit = new HashMap<>();
        purchaseUnit.put("amount", amount);

        Map<String, Object> orderRequest = new HashMap<>();
        orderRequest.put("intent", "CAPTURE");
        orderRequest.put("purchase_units", new Map[]{purchaseUnit});

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(orderRequest, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        }
        throw new TravelExpertException("Failed to create order");
    }

//    @Override
//    public Map<String, Object> captureOrder(String orderId) {
//        String url = baseUrl + "/v2/checkout/orders/" + orderId + "/capture";
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(getAccessToken()); // Add only the Authorization header
//
//        HttpEntity<Void> request = new HttpEntity<>(headers); // No body required
//
//        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
//
//        if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {
//            return response.getBody();
//        }
//        throw new TravelExpertException("Failed to capture order");
//    }

    @Override
    public PayPalCaptureOrderResponse captureOrder(String orderId) {
        String url = baseUrl + "/v2/checkout/orders/" + orderId + "/capture";
        RestTemplate restTemplate = new RestTemplate();

        // Set up headers with Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());

        // Create an HttpEntity with no body
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // Make the POST request to capture the order
        ResponseEntity<PayPalCaptureOrderResponse> response = restTemplate.exchange(
                url, HttpMethod.POST, request, PayPalCaptureOrderResponse.class
        );

        // Check for successful response and return the mapped DTO
        if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        // Throw an exception if the capture fails
        throw new TravelExpertException("Failed to capture order");
    }
}
