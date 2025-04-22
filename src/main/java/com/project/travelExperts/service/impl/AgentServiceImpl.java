package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.dto.request.CreateAgentRequest;
import com.project.travelExperts.data.dto.request.UpdateAgentRequest;
import com.project.travelExperts.data.dto.response.CustomerResponseDto;
import com.project.travelExperts.data.dto.response.FetchAgentDetailsResponse;
import com.project.travelExperts.data.dto.response.FetchCustomersResponse;
import com.project.travelExperts.data.enums.Role;
import com.project.travelExperts.data.model.Admin;
import com.project.travelExperts.data.model.Agent;
import com.project.travelExperts.data.model.Customer;
import com.project.travelExperts.data.repository.AgentRepository;
import com.project.travelExperts.exception.AgentServiceException;
import com.project.travelExperts.exception.TravelExpertException;
import com.project.travelExperts.service.AdminService;
import com.project.travelExperts.service.AgentService;
import com.project.travelExperts.service.CustomerService;
import com.project.travelExperts.service.EmailService;
import com.project.travelExperts.utils.Util;
import com.project.travelExperts.utils.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;
    @Override
    public Agent createAgent(CreateAgentRequest agentRequest, Admin admin) {
        checkIfUserExists(agentRequest.getAgentEmail());
        if (emailExists(agentRequest.getAgentEmail())){
            throw new AgentServiceException("Agent with email " + agentRequest.getAgentEmail() + " already exists");
        }

        Agent agent =  new Agent();
        agent.setAgentEmail(agentRequest.getAgentEmail());
//        agent.setAgentBusPhone(agentRequest.getAgentBusPhone());
        agent.setAgentLastName(agentRequest.getAgentLastName());
//        agent.setAgentMiddleName(agentRequest.getAgentMiddleName());
//        agent.setAgentPosition(agentRequest.getAgentPosition());
        agent.setAgentRole(Role.AGENT);
        agent.setFirstName(agentRequest.getFirstName());
        agent.setPassword(passwordEncoder.encode(agentRequest.getPassword()));
        agent.setManager(admin);
        agent.setPoint(0);
        agent.setEnabled(true);
        agent.setAgentCode(generateAgentCode(agentRequest.getFirstName()));

        return saveAgent(agent);
    }

    @Override
    public Agent saveAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    @Override
    public Agent findByEmail(String email) {
        Optional<Agent> optionalAgent = agentRepository.findByAgentEmail(email);
        if (optionalAgent.isPresent()){
            return optionalAgent.get();
        }
        throw new AgentServiceException("Agent not found");
    }

    @Override
    public Agent findByEmailForLogin(String email) {
        Optional<Agent> optionalAgent = agentRepository.findByAgentEmail(email);
        return optionalAgent.orElse(null);

    }

    @Override
    public Agent findByAgentCode(String agentCode) {

        Optional<Agent> optionalAgent = agentRepository.findByAgentCode(agentCode);
        if (optionalAgent.isPresent()){
            return optionalAgent.get();
        }
        throw new AgentServiceException("Agent not found");
    }

    @Override
    public boolean emailExists(String email) {
        return agentRepository.findByAgentEmail(email).isPresent();
    }

    @Override
    public boolean existsByAgentCode(String agentCode) {
        return agentRepository.existsByAgentCode(agentCode);
    }

    @Override
    public List<Agent> findAll() {
        return agentRepository.findAll();
    }

    @Override
    public List<Agent> fetchAgents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Agent> customerPage = agentRepository.findAll(pageable);
        return customerPage.getContent();
    }

    @Override
    public List<Agent> fetchAgentByAgentMangerId(long agentManagerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Agent> customerPage = agentRepository.findAllByManagerAdminId(pageable,agentManagerId);
        return customerPage.getContent();
    }

    @Override
    public FetchCustomersResponse fetchAgentsCustomers(int page, int size) {
        String currentUserEmail = getCurrentUserEmail();
        Agent agent =  findByEmail(currentUserEmail);
        List<Customer> customers = customerService.fetchCustomersByAgentId(agent.getAgentId(), page, size);
        if (customers.isEmpty()){
            return new FetchCustomersResponse(200,true,"No customers found for this agent");
        }
        List<CustomerResponseDto> customerResponsDtos = customers.stream()
                .map(Util::mapToCustomerResponseForDetails)
                .toList();
        return new FetchCustomersResponse(200,true,"Customers found for this agent", customerResponsDtos);
    }

    @Override
    public FetchAgentDetailsResponse fetchAgentDetails() {
        String currentUserEmail = getCurrentUserEmail();
        Agent agent =  findByEmail(currentUserEmail);
        return new FetchAgentDetailsResponse(200,true,"Agent fetched successfully", Util.mapAgentToAgentResponseForDetials(agent));
    }

    @Override
    public FetchAgentDetailsResponse updateAgent(long agentId, UpdateAgentRequest updateAgentRequest) {
        validateValidateUpdateAgentRequest(updateAgentRequest);
        Agent agent = findByAgentId(agentId);
        if (null != updateAgentRequest.getFirstName() && !updateAgentRequest.getFirstName().isBlank()){
            agent.setFirstName(updateAgentRequest.getFirstName());
        }
        if (null != updateAgentRequest.getAgentLastName() && !updateAgentRequest.getAgentLastName().isBlank()){
            agent.setAgentLastName(updateAgentRequest.getAgentLastName());
        }
        if (null != updateAgentRequest.getAgentMiddleName() && !updateAgentRequest.getAgentMiddleName().isBlank()){
            agent.setAgentMiddleName(updateAgentRequest.getAgentMiddleName());
        }
        if (null != updateAgentRequest.getAgentBusPhone() && !updateAgentRequest.getAgentBusPhone().isBlank()){
            agent.setAgentBusPhone(updateAgentRequest.getAgentBusPhone());
        }
        if (null != updateAgentRequest.getAgentPosition() && !updateAgentRequest.getAgentPosition().isBlank()){
            agent.setAgentPosition(updateAgentRequest.getAgentPosition());
        }
        if (null != updateAgentRequest.getImageUrl() && !updateAgentRequest.getImageUrl().isBlank()){
            agent.setImageUrl(updateAgentRequest.getImageUrl());
        }

        agent = saveAgent(agent);
        return new FetchAgentDetailsResponse(200,true,"Agent updated successfully", Util.mapAgentToAgentResponseForDetials(agent));
    }

    @Override
    public Agent findByAgentId(long agentId) {
        return agentRepository.findById(agentId).orElseThrow(()-> new AgentServiceException("Agent not found"));
    }

    @Override
    public Agent deativateAgent(long agentId) {
        Agent agent = findByAgentId(agentId);
        agent.setEnabled(false);
        return saveAgent(agent);
    }

    @Override
    public Agent ativateAgent(long agentId) {
        Agent agent = findByAgentId(agentId);
        agent.setEnabled(true);
        return saveAgent(agent);
    }

    @Override
    public boolean existsByUsername(String username) {
        return agentRepository.existsByAgentEmail(username);
    }

    private void validateValidateUpdateAgentRequest(UpdateAgentRequest updateAgentRequest){
        if ((null == updateAgentRequest.getFirstName() || updateAgentRequest.getFirstName().isBlank() || updateAgentRequest.getFirstName().isEmpty())
        &&(null == updateAgentRequest.getAgentLastName() || updateAgentRequest.getAgentLastName().isEmpty() || updateAgentRequest.getAgentLastName().isBlank())
                &&(null == updateAgentRequest.getAgentMiddleName() || updateAgentRequest.getAgentMiddleName().isEmpty() || updateAgentRequest.getAgentMiddleName().isBlank())
                &&(null == updateAgentRequest.getAgentBusPhone() || updateAgentRequest.getAgentBusPhone().isEmpty() || updateAgentRequest.getAgentBusPhone().isBlank())
                &&(null == updateAgentRequest.getAgentPosition() || updateAgentRequest.getAgentPosition().isEmpty() || updateAgentRequest.getAgentPosition().isBlank())
                &&(null == updateAgentRequest.getImageUrl() || updateAgentRequest.getImageUrl().isBlank() || updateAgentRequest.getImageUrl().isEmpty())
        ){
            throw new AgentServiceException("Please provide at least one field to update");
        }
    }

    private String generateAgentCode(String firstName) {
        // Get the first three letters of the first name
        String firstThreeLetters = firstName.length() >= 3 ? firstName.substring(0, 3).toUpperCase() : firstName.toUpperCase();

        // Generate a random number of length 3
        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900); // Generates a number between 100 and 999

        // Concatenate the first three letters and the random number
        String agentCode =  firstThreeLetters + randomNumber;
        if (agentRepository.existsByAgentCode(agentCode)){
            generateAgentCode(firstName);
        }

        return agentCode;
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsername();
        }
        return null;
    }

    private void checkIfUserExists(String username) {
        if (customerService.existsByUsername(username)) {
            throw new TravelExpertException("Customer with email: "+username+" already exists");
        } else if (agentRepository.existsByAgentEmail(username)) {
            throw new TravelExpertException("User with email: "+username+" already exists");
        } else if (adminService.existsByUsername(username)) {
            throw new TravelExpertException("User with email: "+username+" already exists");
        }
    }
}
