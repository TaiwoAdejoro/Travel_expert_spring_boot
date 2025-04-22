package com.project.travelExperts.controller;

import com.project.travelExperts.data.dto.request.ReviewRequest;
import com.project.travelExperts.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

     @PostMapping("/add")
     public ResponseEntity<?> addReview(@Valid  @RequestBody ReviewRequest reviewRequest) {
         return ResponseEntity.ok().body(reviewService.addReview(reviewRequest));
     }

     @GetMapping("/get/{packageId}")
        public ResponseEntity<?> getAllPackageReview(@PathVariable @Valid @NotNull(message = "Package Id is required") long packageId) {
            return ResponseEntity.ok().body(reviewService.getAllPackageReview(packageId));
        }
}
