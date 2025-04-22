package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.request.CreateAgentRequest;
import com.project.travelExperts.data.dto.request.UpdateAgentRequest;
import com.project.travelExperts.data.dto.response.FetchAgentDetailsResponse;
import com.project.travelExperts.data.dto.response.FetchCustomersResponse;
import com.project.travelExperts.data.model.Admin;
import com.project.travelExperts.data.model.Agent;

import java.util.List;

public interface AgentService {
    Agent createAgent(CreateAgentRequest agent, Admin admin);
    Agent saveAgent(Agent agent);

    Agent findByEmail(String email);
    Agent findByEmailForLogin(String email);
    Agent findByAgentCode(String email);
    boolean emailExists(String email);

    boolean existsByAgentCode(String agentCode);

    List<Agent> findAll();

    List<Agent> fetchAgents(int page, int size);

    List<Agent> fetchAgentByAgentMangerId(long agentManagerId, int page, int size);

    FetchCustomersResponse fetchAgentsCustomers(int page, int size);

    FetchAgentDetailsResponse fetchAgentDetails();

    FetchAgentDetailsResponse updateAgent(long agentId, UpdateAgentRequest updateAgentRequest);

    Agent findByAgentId(long agentId);

    Agent deativateAgent(long agentId);

    Agent ativateAgent(long agentId);

    boolean existsByUsername(String username);
}
