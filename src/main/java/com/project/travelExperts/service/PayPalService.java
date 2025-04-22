package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.response.PayPalCaptureOrderResponse;

import java.util.Map;

public interface PayPalService {
     String getAccessToken();

     Map<String, Object> createOrder(double total, String currency);

    PayPalCaptureOrderResponse captureOrder(String orderId);
}
