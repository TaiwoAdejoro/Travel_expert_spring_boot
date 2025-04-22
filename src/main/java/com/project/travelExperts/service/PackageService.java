package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.request.CreatePackageRequest;
import com.project.travelExperts.data.dto.request.UpdatePackageRequest;
import com.project.travelExperts.data.dto.response.PackageResponse;
import com.project.travelExperts.data.model.Package;

import java.text.ParseException;

public interface PackageService {
    PackageResponse createPackage(CreatePackageRequest request) throws ParseException;
    PackageResponse fetchPackages(int page, int seize);

    Package savePackage(Package newPackage);

    PackageResponse updatePackage(long packageId , UpdatePackageRequest request);

    PackageResponse fetchPackageById(long packageId);

    Package getPackageByID(long packageId);

    PackageResponse deletePackage(long packageId);

    PackageResponse fetchAllPackages(int page, int size);

    PackageResponse ratePackage(long packageId, int rating);
}
