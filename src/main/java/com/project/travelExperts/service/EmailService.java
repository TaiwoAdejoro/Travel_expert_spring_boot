package com.project.travelExperts.service;

import com.project.travelExperts.data.model.Customer;

public interface EmailService {
    void sendAgentManagerCreationEmail(String to, String agentPrefix, String password);
    void sendSimpleMessage(String to, String subject, String text);

    void sendCustomerVerificationMail(Customer newCustomer, String verificationToken);
}
