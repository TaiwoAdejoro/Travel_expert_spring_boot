package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.dto.request.ReviewRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.data.model.Customer;
import com.project.travelExperts.data.model.Package;
import com.project.travelExperts.data.model.Review;
import com.project.travelExperts.data.repository.ReviewRepository;
import com.project.travelExperts.service.CustomerService;
import com.project.travelExperts.service.PackageService;
import com.project.travelExperts.service.ReviewService;
import com.project.travelExperts.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    PackageService packageService;

    @Autowired
    CustomerService customerService;
    @Override
    public Review saveReview(Review review) {
        return repository.save(review);
    }

    @Override
    public List<Review> findByAPackage_Id(Long packageId) {
        return repository.findByAPackage_Id(packageId);
    }

    @Override
    public List<Review> findByCreatedBy(String createdBy) {
        return repository.findByCreatedBy(createdBy);
    }

    @Override
    public List<Review> findByAPackage_IdAndCreatedBy(Long packageId, String createdBy) {
        return repository.findByAPackage_IdAndCreatedBy(packageId,  createdBy);
    }


    @Override
    public BaseResponse<?> addReview(ReviewRequest request) {
        Package aPackage = packageService.getPackageByID(request.getPackageId());
        if (aPackage.getReviews() == null) {
            aPackage.setReviews(new ArrayList<>());
        }

        Customer customer =  customerService.getCustomerByEmail(getCurrentUserEmail());

        Review review = new Review();
        review.setContent(request.getText());
        review.setAPackage(aPackage);
        review.setCreatedBy(customer.getFirstName() +" "+ customer.getLastName());

        // Add the review to the existing collection
        aPackage.getReviews().add(review);

        // Update the total reviews count
        aPackage.setTotalReviews(aPackage.getTotalReviews() + 1);

        // Save the review and package
        repository.save(review);
        packageService.savePackage(aPackage);

        return new BaseResponse<>(200, true, "Review added successfully", Util.mapToPackageResponse(aPackage));
    }

    @Override
    public BaseResponse<?> getAllPackageReview(long packageId){
        List<Review> reviews =  findByAPackage_Id(packageId);
        if (reviews.isEmpty()){
            return new BaseResponse<>(200, true, "No reviews found for this package", null);
        }
        return new BaseResponse<>(200, true, "Reviews fetched successfully", reviews.stream().map(Util::mapToReviewResponse).toList());
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsername();
        }

        return null;
    }
}
