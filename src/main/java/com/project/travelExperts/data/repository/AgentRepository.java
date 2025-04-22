package com.project.travelExperts.data.repository;

import com.project.travelExperts.data.model.Agent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent,Long> {
    Optional<Agent> findByAgentEmail(String agentEmail);
    Optional<Agent> findByAgentCode(String agentCode);

    Page<Agent> findAllByManagerAdminId(Pageable pageable, long adminId);

    boolean existsByAgentCode(String agentCode);
    boolean existsByAgentEmail(String agentCode);
}
