package com.project.travelExperts.controller;

import com.project.travelExperts.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private PayPalService payPalService;

    @PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestParam double total, @RequestParam String currency) {
        return payPalService.createOrder(total, currency);
    }

//    @PostMapping("/capture-order/{orderId}")
//    public Map<String, Object> captureOrder(@PathVariable String orderId) {
//        return payPalService.captureOrder(orderId);
//    }
}
