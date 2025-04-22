package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.dto.request.CreatePackageRequest;
import com.project.travelExperts.data.dto.request.UpdatePackageRequest;
import com.project.travelExperts.data.dto.response.PackageResponse;
import com.project.travelExperts.data.dto.response.PackageResponseDto;
import com.project.travelExperts.data.model.Package;
import com.project.travelExperts.data.model.Review;
import com.project.travelExperts.data.repository.PackageRepository;
import com.project.travelExperts.exception.PackageServiceException;
import com.project.travelExperts.service.PackageService;
import com.project.travelExperts.service.ProductService;
import com.project.travelExperts.service.SupplierService;
import com.project.travelExperts.utils.Util;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Override
    @Transactional
    public PackageResponse createPackage(CreatePackageRequest request) throws ParseException {
        Package newPackage = createPackageFromRequest(request);
        PackageResponseDto packageResponse = Util.mapToPackageResponse(newPackage);
        return new PackageResponse(200,true,"Package created successfully", packageResponse);
    }

    @Override
    public PackageResponse fetchPackages(int page, int seize) {
        Pageable pageable =  PageRequest.of(page, seize);
        String currentAgent = getCurrentUserEmail();
        List<Package> packages = packageRepository.findAllByCreatedBy(pageable, currentAgent).getContent();
        return new PackageResponse(200,true,"Package fetched successfully", packages.stream().map(Util::mapToPackageResponse).toList());
    }

    private Package createPackageFromRequest(CreatePackageRequest request) throws ParseException {
        String currentAgent =  getCurrentUserEmail();
        Package newPackage = new Package();
        newPackage.setName(request.getName());
        newPackage.setDescription(request.getDescription());
        newPackage.setCreatedBy(currentAgent);
        newPackage.setUpdatedBy(currentAgent);
        newPackage.setBasePrice(request.getBasePrice());
        newPackage.setAgencyCommission(request.getAgencyCommission());
        newPackage.setReviews(new ArrayList<>());
        newPackage.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getStartDate()));
        newPackage.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getEndDate()));
//        newPackage.setProducts(createProductsFromRequest(request.getProducts(),request.getSupplierName()));
        return savePackage(newPackage);
    }

    @Override
    public Package savePackage(Package newPackage) {
        return packageRepository.save(newPackage);
    }

//    private  List<Product> createProductsFromRequest(List<CreateProductRequest> productRequests,String supplierName){
//        List<Product> products = new ArrayList<>();
//        for (CreateProductRequest productRequest : productRequests) {
//            Product product = new Product();
//            product.setProductName(productRequest.getProductName());
//            product.setProductCost(productRequest.getProductCost());
//            product.setAvailable(true);
//            product.setCreatedBy(getCurrentUserEmail());
//            product = productService.saveProduct(product);
//            product.setProductSupplier(createSupplierFromRequest(supplierName,product));
//            products.add(product);
//        }
//        return productService.saveProducts(products);
//    }

//    private Supplier createSupplierFromRequest(String supplierName,Product product){
//        Supplier supplier = new Supplier();
//        supplier.setName(supplierName);
//        supplier.setProduct(product);
//        supplier.setCreatedBy(getCurrentUserEmail());
//        return supplierService.saveSupplier(supplier);
//    }
    @Override
    public PackageResponse updatePackage(long packageId, UpdatePackageRequest request) {
        validateUpdatePackageRequest(request);
        Package aPackage = getPackageByID(packageId);
        if (null != request.getName() && !request.getName().isEmpty()){
            aPackage.setName(request.getName());
        }
        if (null != request.getDescription() && !request.getDescription().isEmpty()){
            aPackage.setDescription(request.getDescription());
        }
        if (null != request.getBasePrice() && request.getBasePrice().doubleValue() > 0){
            aPackage.setBasePrice(request.getBasePrice());
        }
        if (null != request.getAgencyCommission() && request.getAgencyCommission().doubleValue() > 0){
            aPackage.setAgencyCommission(request.getAgencyCommission());
        }
        aPackage.setUpdatedBy(getCurrentUserEmail());
        Package updatedPackage = packageRepository.save(aPackage);
        return new PackageResponse(200,true,"Package updated successfully", Util.mapToPackageResponse(updatedPackage));
    }

    private void validateUpdatePackageRequest(UpdatePackageRequest updatePackageRequest){
        if ((null == updatePackageRequest.getName() || updatePackageRequest.getName().isEmpty())
        &&(null == updatePackageRequest.getDescription() ||updatePackageRequest.getDescription().isEmpty())
                &&(null ==  updatePackageRequest.getBasePrice() || updatePackageRequest.getBasePrice().doubleValue() <=0)
                &&(null ==  updatePackageRequest.getAgencyCommission() || updatePackageRequest.getAgencyCommission().doubleValue() <=0)
        ){
            throw new PackageServiceException("Please provide at least one field to update");
        }
    }

    @Override
    public PackageResponse fetchPackageById(long packageId) {
        Optional<Package> optionalPackage =  packageRepository.findById(packageId);
        if (optionalPackage.isEmpty()){
            throw new PackageServiceException("Package not found");
        }
        PackageResponseDto packageResponseDto =  Util.mapToPackageResponse(optionalPackage.get());
        return new PackageResponse(200,true,"Package fetched successfully", packageResponseDto);
    }
    @Override
    public Package getPackageByID(long packageId){
        Optional<Package> optionalPackage =  packageRepository.findById(packageId);
        if (optionalPackage.isEmpty()){
            throw new PackageServiceException("Package not found");
        }
        return optionalPackage.get();
    }

    @Override
    public PackageResponse deletePackage(long packageId) {
        packageRepository.deleteById(packageId);
        return new PackageResponse(200,true,"Package deleted successfully",null);
    }

    @Override
    public PackageResponse fetchAllPackages(int page, int size) {
        return new PackageResponse(200,true,"Package fetched successfully", packageRepository.findAll(PageRequest.of(page, size)).getContent().stream().map(Util::mapToPackageResponse).toList());
    }

    @Override
    public PackageResponse ratePackage(long packageId, int rating) {

        if (rating < 1 || rating > 5) {
            throw new PackageServiceException("Rating must be between 1 and 5");
        }

        Package aPackage = getPackageByID(packageId);
        aPackage.setTotalRatings(aPackage.getTotalRatings() + 1);
        aPackage.setNumberOfRatingStars(aPackage.getNumberOfRatingStars() + rating);

        // Calculate the average rating
        double averageRating = (double) aPackage.getNumberOfRatingStars() / aPackage.getTotalRatings();
        aPackage.setAverageRatings(averageRating);
        // Save the updated package
        Package updatedPackage = packageRepository.save(aPackage);
        return new PackageResponse(200, true, "Package rated successfully. Average rating: " + averageRating, Util.mapToPackageResponse(updatedPackage));
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsername();
        }

        return null;
    }
}
