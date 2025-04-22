package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.dto.request.CreateAgentManagerRequest;
import com.project.travelExperts.data.dto.request.CreateAgentRequest;
import com.project.travelExperts.data.dto.request.UpdateAgentManagerRequest;
import com.project.travelExperts.data.dto.response.*;
import com.project.travelExperts.data.enums.Role;
import com.project.travelExperts.data.model.Admin;
import com.project.travelExperts.data.model.Agent;
import com.project.travelExperts.data.model.Customer;
import com.project.travelExperts.data.repository.AdminRepository;
import com.project.travelExperts.data.repository.AgentRepository;
import com.project.travelExperts.exception.AdminServiceException;
import com.project.travelExperts.exception.TravelExpertException;
import com.project.travelExperts.service.AdminService;
import com.project.travelExperts.service.AgentService;
import com.project.travelExperts.service.CustomerService;
import com.project.travelExperts.service.EmailService;
import com.project.travelExperts.utils.Util;
import com.project.travelExperts.utils.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl  implements AdminService {

    @Autowired
    private  AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;
    @Lazy
    @Autowired
    private AgentService agentService;

    @Autowired
    private CustomerService customerService;


    @Override
    public Admin findAdminByUsername(String email) {

        Optional<Admin> optionalAdmin = adminRepository.findByUsername(email);
        if (optionalAdmin.isPresent()){
            return optionalAdmin.get();
        }

        throw new AdminServiceException("Admin not found with email: " + email);
    }

    @Override
    public Admin findAdminByUsernameForLogin(String email) {

        Optional<Admin> optionalAdmin = adminRepository.findByUsername(email);
        return optionalAdmin.orElse(null);

    }

    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public CreateAgentManagerResponse createAgentManager(CreateAgentManagerRequest createAgentManagerRequest) {
       checkIfUserExists(createAgentManagerRequest.getUsername());
        String currentUserEmail = getCurrentUserEmail();

        adminExists(createAgentManagerRequest.getUsername());

        Admin admin = findAdminByUsername(currentUserEmail);

        Admin agentManager = createAgentAdmin(createAgentManagerRequest, admin.getUsername());

        agentManager = saveAdmin(agentManager);
//        emailService.sendAgentManagerCreationEmail(agentManager.getUsername(),generateAgentCode(agentManager.getUsername()),createAgentManagerRequest.getPassword());
        return new CreateAgentManagerResponse(201,true,"Agent manager created successfully",Util.mapToAdminResponse(agentManager));
    }

    @Override
    public CreateAgentResponse createAgent(CreateAgentRequest createAgentRequest) {
        String currentUserEmail = getCurrentUserEmail();
        Admin admin = findAdminByUsername(currentUserEmail);
        Agent agent = agentService.createAgent(createAgentRequest, admin);
        AgentResponseDto agentResponseDto = Util.mapAgentToAgentResponseForDetials(agent);
        return new CreateAgentResponse(201,true,"Agent created successfully", agentResponseDto);
    }

    @Override
    public FetchCustomersResponse fetchCustomers(int page, int size) {
        List<Customer> customers =  customerService.fetchCustomers(page, size);
        List<CustomerResponseDto> customerResponsDtos = new ArrayList<>();
        if (!customers.isEmpty()){
            for (Customer customer : customers) {
                CustomerResponseDto customerResponseDto = Util.mapToCustomerResponse(customer);
                customerResponsDtos.add(customerResponseDto);
            }

            return new FetchCustomersResponse(200,true,"Customers fetched successfully", customerResponsDtos);
        }
        return new FetchCustomersResponse(200,true,"There are no customers to fetch", customerResponsDtos);

    }

    @Override
    public FetchAgentsResponse fetchAgents(int page, int size) {
        List<Agent> agents =  agentService.fetchAgents(page, size);
        List<AgentResponseDto> agentResponsDtos = new ArrayList<>();
        if (!agents.isEmpty()){
            for (Agent agent : agents) {
                AgentResponseDto agentResponseDto = Util.mapAgentToAgentResponse(agent);
                agentResponsDtos.add(agentResponseDto);
            }

            return new FetchAgentsResponse(200,true,"Agents fetched successfully", agentResponsDtos);
        }
        return new FetchAgentsResponse(200,true,"There are no agents to fetch", agentResponsDtos);

    }

    @Override
    public FetchAgentManagersResponse fetchAgentManagers(int page, int size) {
        List<Admin> admins =  findAll(page, size);
        List<AdminResponseDto> adminResponsDtos = new ArrayList<>();
        if (!admins.isEmpty()){
            for (Admin admin : admins) {
                AdminResponseDto adminResponseDto = Util.mapToAdminResponse(admin);
                adminResponsDtos.add(adminResponseDto);
            }

            return new FetchAgentManagersResponse(200,true,"Agents fetched successfully", adminResponsDtos);
        }
        return new FetchAgentManagersResponse(200,true,"There are no agents to fetch", adminResponsDtos);

    }

    @Override
    public FetchAgentsResponse fetchAgentAssignedToAdminManager(int page, int size) {
        String currentUserEmail = getCurrentUserEmail();
        Admin admin =  findAdminByUsername(currentUserEmail);
        List<Agent> agents =  agentService.fetchAgentByAgentMangerId(admin.getAdminId(), page, size);
        List<AgentResponseDto> agentResponsDtos = new ArrayList<>();
        if (!agents.isEmpty()){
            for (Agent agent:agents){
                AgentResponseDto agentResponseDto = Util.mapAgentToAgentResponseForDetials(agent);
                agentResponsDtos.add(agentResponseDto);
            }

            return new FetchAgentsResponse(200,true,"Agents fetched successfully", agentResponsDtos);
        }
        return new FetchAgentsResponse(200,true,"You have not created any agent", agentResponsDtos);
    }

    @Override
    public FetchAdminDetailsResponse fetchAdminDetails() {
        String currentUserEmail = getCurrentUserEmail();
        Admin admin =  findAdminByUsername(currentUserEmail);
        return new FetchAdminDetailsResponse(200,true,"Admin fetched successfully", Util.mapToAdminResponseForDetails(admin));
    }

    @Override
    public Admin findAdminById(long adminId) {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        if (optionalAdmin.isPresent()){
            return optionalAdmin.get();
        }
        throw new AdminServiceException("Admin not found with id: " + adminId);
    }
    @Override
    public FetchAgentManagerResponse deactivateAgentManager(long agentManagerId) {
        String currentAdminEmail =  getCurrentUserEmail();
        Admin  agentManager =  findAdminById(agentManagerId);
        agentManager.setEnabled(false);
        agentManager.setUpdatedBy(currentAdminEmail);
        agentManager =  saveAdmin(agentManager);
        AdminResponseDto adminResponseDto =  Util.mapToAdminResponse(agentManager);
        // send email
        return new FetchAgentManagerResponse(200,true,"Agent manager deactivated successfully",adminResponseDto);
    }

    @Override
    public FetchAgentManagerResponse activateAgentManager(long agentManagerId) {
        Admin  agentManager =  findAdminById(agentManagerId);
        agentManager.setEnabled(true);
        agentManager.setUpdatedBy(getCurrentUserEmail());
        agentManager =  saveAdmin(agentManager);
        AdminResponseDto adminResponseDto =  Util.mapToAdminResponse(agentManager);
        // send email
        return new FetchAgentManagerResponse(200,true,"Agent manager Activated successfully",adminResponseDto);
    }

    @Override
    public FetchAgentManagerResponse updateAgentManager(long agentManagerId, UpdateAgentManagerRequest updateAgentManagerRequest) {
        validateUpdateAgentManagerRequest(updateAgentManagerRequest);
        Admin agentManager =  findAdminById(agentManagerId);
        if(updateAgentManagerRequest.getAgentManagerName() != null && !updateAgentManagerRequest.getAgentManagerName().isEmpty()){
            agentManager.setAgentManagerName(updateAgentManagerRequest.getAgentManagerName());
        }

        if(updateAgentManagerRequest.getImageUrl() != null && !updateAgentManagerRequest.getImageUrl().isEmpty()){
            agentManager.setImageUrl(updateAgentManagerRequest.getImageUrl());
        }

        agentManager = saveAdmin(agentManager);

        return new FetchAgentManagerResponse(200,true,"Agent manager updated successfully",Util. mapToAdminResponseForDetails(agentManager));
    }

    @Override
    public FetchAgentDetailsResponse deactivateAgent(long agentId) {
        Agent agent =  agentService.deativateAgent(agentId);
        return new FetchAgentDetailsResponse(200,true,"Agent deactivated successfully",Util.mapAgentToAgentResponseForDetials(agent));
    }


    @Override
    public FetchAgentDetailsResponse activateAgent(long agentId) {
        Agent agent =  agentService.ativateAgent(agentId);
        return new FetchAgentDetailsResponse(200,true,"Agent activated successfully",Util.mapAgentToAgentResponseForDetials(agent));
    }

    @Override
    public boolean existsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    private void validateUpdateAgentManagerRequest(UpdateAgentManagerRequest request){
        if ((null == request.getAgentManagerName()  || request.getAgentManagerName().isEmpty() || request.getAgentManagerName().isBlank())
        && (request.getImageUrl() == null || request.getImageUrl().isEmpty() || request.getImageUrl().isBlank())){
            throw new AdminServiceException("Please provide a name or image to update");
        }
    }

    public List<Admin> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Admin> adminPage = adminRepository.findAll(pageable);
        return adminPage.getContent();
    }
    private Admin createAgentAdmin(CreateAgentManagerRequest createAgentManagerRequest,String createdBy) {
        Admin admin = new Admin();
        admin.setUsername(createAgentManagerRequest.getUsername());
        admin.setPassword(passwordEncoder.encode(createAgentManagerRequest.getPassword()));
        admin.setRole(Role.AGENT_MANAGER);
        admin.setCreatedBy(createdBy);
        admin.setUpdatedBy(createdBy);
        admin.setAgentManagerName(createAgentManagerRequest.getAgentManagerName());
        admin.setCreatedDate(new Date());
        admin.setAgents(new ArrayList<>());
        admin.setEnabled(true);
        return admin;
    }



    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsername();
        }
        return null;
    }

    private void adminExists(String username){
        Optional<Admin> admin =  adminRepository.findByUsername(username);
        if (admin.isPresent()){
            throw new AdminServiceException("Admin with email: " + username + " already exists");
        }
    }

    private void checkIfUserExists(String username) {
        if (customerService.existsByUsername(username)) {
            throw new TravelExpertException("Customer with email: "+username+" already exists");
        } else if (agentService.existsByUsername(username)) {
            throw new TravelExpertException("User with email: "+username+" already exists");
        } else if (adminRepository.existsByUsername(username)) {
            throw new TravelExpertException("User with email: "+username+" already exists");
        }
    }
}
