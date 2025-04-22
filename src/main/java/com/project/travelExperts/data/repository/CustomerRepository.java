package com.project.travelExperts.data.repository;

import com.project.travelExperts.data.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer,Long> {
    Optional<Customer>findByEmail(String email);

    boolean existsByEmail(String email);

    Customer findTopByOrderByCreatedAtDesc();

    Page<Customer> findAllByAgentAgentId(Pageable pageable, long agentId);

}
