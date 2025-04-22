package com.project.travelExperts.controller;

import com.project.travelExperts.data.dto.request.CreatePackageRequest;
import com.project.travelExperts.data.dto.request.UpdatePackageRequest;
import com.project.travelExperts.data.dto.response.PackageResponse;
import com.project.travelExperts.service.PackageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/package")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @PostMapping("/create")
    public ResponseEntity<PackageResponse> createPackage(@RequestBody @Valid CreatePackageRequest request) throws ParseException {
        PackageResponse response = packageService.createPackage(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<PackageResponse> fetchPackages(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PackageResponse response = packageService.fetchPackages(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<PackageResponse> fetchAllPackages(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PackageResponse response = packageService.fetchAllPackages(page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{packageId}")
    public ResponseEntity<PackageResponse> fetchPackageById(@PathVariable  long packageId){
        PackageResponse response = packageService.fetchPackageById(packageId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update/{packageId}")
    public ResponseEntity<PackageResponse> updatePackage(@PathVariable long packageId, @RequestBody @Valid UpdatePackageRequest request) {
        PackageResponse response = packageService.updatePackage(packageId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{packageId}")
    public ResponseEntity<PackageResponse> deletePackage(@PathVariable @Valid @NotNull(message = "Package Id is required") long packageId) {
        PackageResponse response = packageService.deletePackage(packageId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/rate/{packageId}")
    public ResponseEntity<PackageResponse> ratePackage(@PathVariable @Valid @NotNull(message = "Package Id is required") long packageId, @RequestParam @NotNull(message = "Rating is required") int rating) {
        PackageResponse response = packageService.ratePackage(packageId, rating);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
