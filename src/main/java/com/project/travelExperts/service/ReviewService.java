package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.request.ReviewRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.data.dto.response.PackageResponse;
import com.project.travelExperts.data.model.Review;

import java.util.List;

public interface ReviewService {
    Review saveReview(Review review);

    List<Review> findByAPackage_Id(Long packageId);
    List<Review> findByCreatedBy(String createdBy);
    List<Review> findByAPackage_IdAndCreatedBy(Long packageId, String createdBy);

    BaseResponse<?> addReview(ReviewRequest request);

    BaseResponse<?> getAllPackageReview(long packageId);
}
