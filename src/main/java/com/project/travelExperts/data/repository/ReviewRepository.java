package com.project.travelExperts.data.repository;

import com.project.travelExperts.data.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.aPackage.id =:packageId")
    List<Review> findByAPackage_Id(@Param("packageId")Long packageId);
    List<Review> findByCreatedBy(String createdBy);
    @Query("SELECT r FROM Review r WHERE r.aPackage.id =:packageId AND r.createdBy =:createdBy")
    List<Review> findByAPackage_IdAndCreatedBy(@Param("packageId")Long packageId, @Param("createdBy")String createdBy);
}
