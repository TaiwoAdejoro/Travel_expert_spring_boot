package com.project.travelExperts.data.repository;

import com.project.travelExperts.data.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Custom query methods can be defined here if needed
    // For example, find by name or other attributes
}
