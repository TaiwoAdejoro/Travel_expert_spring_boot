package com.project.travelExperts.data.repository;

import com.project.travelExperts.data.model.Package;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

    Page<Package> findAllByCreatedBy(Pageable pageable, String createdBy);

}
