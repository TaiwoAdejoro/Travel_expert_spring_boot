package com.project.travelExperts.utils;

import com.project.travelExperts.exception.TravelExpertException;
import com.project.travelExperts.service.AdminService;
import com.project.travelExperts.service.AgentService;
import com.project.travelExperts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private AdminService adminService;

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
