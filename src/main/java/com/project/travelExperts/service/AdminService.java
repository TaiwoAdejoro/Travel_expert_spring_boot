package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.request.CreateAgentManagerRequest;
import com.project.travelExperts.data.dto.request.CreateAgentRequest;
import com.project.travelExperts.data.dto.request.UpdateAgentManagerRequest;
import com.project.travelExperts.data.dto.response.*;
import com.project.travelExperts.data.model.Admin;

public interface AdminService {

    Admin findAdminByUsername(String email);

    Admin findAdminByUsernameForLogin(String email);

    Admin saveAdmin(Admin admin);

    CreateAgentManagerResponse createAgentManager(CreateAgentManagerRequest createAgentManagerRequest);

    CreateAgentResponse createAgent(CreateAgentRequest createAgentRequest);

    FetchCustomersResponse fetchCustomers(int page, int size);

    FetchAgentsResponse fetchAgents(int page, int size);

    FetchAgentManagersResponse fetchAgentManagers(int page, int size);

    FetchAgentsResponse fetchAgentAssignedToAdminManager(int page, int size);

    FetchAdminDetailsResponse fetchAdminDetails();

    Admin findAdminById(long adminId);

    FetchAgentManagerResponse deactivateAgentManager(long agentManagerId);

    FetchAgentManagerResponse activateAgentManager(long agentManagerId);

    FetchAgentManagerResponse updateAgentManager(long agentManagerId, UpdateAgentManagerRequest updateAgentManagerRequest);

    FetchAgentDetailsResponse deactivateAgent(long agentId);

    FetchAgentDetailsResponse activateAgent(long agentId);

    boolean existsByUsername(String username);
}
