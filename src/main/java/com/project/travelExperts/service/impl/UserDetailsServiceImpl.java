package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.model.Admin;
import com.project.travelExperts.data.model.Agent;
import com.project.travelExperts.data.model.Customer;
import com.project.travelExperts.data.repository.AdminRepository;
import com.project.travelExperts.data.repository.AgentRepository;
import com.project.travelExperts.data.repository.CustomerRepository;
import com.project.travelExperts.exception.AppAuthException;
import com.project.travelExperts.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@RequiredArgsConstructor
@Service
@Component
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private  AdminRepository adminRepository;
    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    private  AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user if user is an admin
        Optional<Admin> optionalAdmin =  adminRepository.findByUsername(username);
        if (optionalAdmin.isPresent()) {
            return UserDetailsImpl.build(optionalAdmin.get());
        }

        // Fetch user if user is a customer
        Optional<Customer> optionalCustomer =  customerRepository.findByEmail(username);
        if (optionalCustomer.isPresent()) {
            return UserDetailsImpl.build(optionalCustomer.get());
        }

        // Fetch user if user is an agent
        Optional<Agent> optionalAgent =  agentRepository.findByAgentEmail(username);
        if (optionalAgent.isPresent()){
            return UserDetailsImpl.build(optionalAgent.get());
        }

       throw new AppAuthException("Bad credentials");
    }
}
