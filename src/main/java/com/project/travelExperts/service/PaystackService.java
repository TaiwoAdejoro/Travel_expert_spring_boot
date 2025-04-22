package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.response.PaystcakVerifyTransactionResponse;

public interface PaystackService {
    PaystcakVerifyTransactionResponse verifyTransaction(String reference);
}
