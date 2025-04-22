package com.project.travelExperts.controller;

import com.project.travelExperts.data.dto.request.UpdateCustomerRequest;
import com.project.travelExperts.service.CustomerService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<?> fetchCustomerDetails() {
        return ResponseEntity.ok(customerService.fetchCustomerDetails());
    }

    @PatchMapping("/update/{customerId}")
    public ResponseEntity<?> updateCustomerDetails(@PathVariable long customerId, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return ResponseEntity.ok(customerService.updateCustomerDetails(customerId,updateCustomerRequest));
    }
}
