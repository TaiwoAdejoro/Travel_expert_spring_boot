package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.dto.request.CreateSupplierRequest;
import com.project.travelExperts.data.dto.request.UpdateSupplierRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.data.dto.response.SupplierResponse;
import com.project.travelExperts.data.dto.response.SupplierResponseDto;
import com.project.travelExperts.data.model.Supplier;
import com.project.travelExperts.data.repository.SupplierRepository;
import com.project.travelExperts.exception.SupplierServiceException;
import com.project.travelExperts.service.ProductService;
import com.project.travelExperts.service.SupplierService;
import com.project.travelExperts.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductService productService;
    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(long supplierId) {
        supplierRepository.deleteById(supplierId);
    }

    @Override
    public void deleteSupplier(Supplier supplier) {
        supplierRepository.delete(supplier);
    }

    @Override
    public SupplierResponse createNewSupplier(CreateSupplierRequest createSupplierRequest) {
        Supplier supplier = new Supplier();
        supplier.setName(createSupplierRequest.getSupplierName());
        supplier.setProduct(productService.findById(createSupplierRequest.getProductId()));
        supplier.setCreatedBy(getCurrentUserEmail());
        return new SupplierResponse(201, true, "Supplier created successfully", Util.mapToSupplierResponse(saveSupplier(supplier)));
    }

    @Override
    public SupplierResponse findAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierResponseDto> supplierResponseDtos = suppliers.stream().map(Util::mapToSupplierResponse).toList();
        return new SupplierResponse(200, true, "Suppliers retrieved successfully", supplierResponseDtos);
    }

    @Override
    public SupplierResponse findSupplierById(long supplierId) {
        Supplier supplier = findById(supplierId);
        return new SupplierResponse(200, true, "Supplier retrieved successfully", Util.mapToSupplierResponse(supplier));
    }

    @Override
    public Supplier findById(long supplierId) {
        return supplierRepository.findById(supplierId).orElseThrow(() -> new SupplierServiceException("Supplier not found"));
    }

    @Override
    public BaseResponse<?> updateSupplier(long supplierId, UpdateSupplierRequest updateSupplierRequest) {
        validateUpdateSupplierRequest(updateSupplierRequest);
        Supplier supplier =  findById(supplierId);
        if (updateSupplierRequest.getSupplierName() != null && !updateSupplierRequest.getSupplierName().isEmpty()) {
            supplier.setName(updateSupplierRequest.getSupplierName());
        }
        if (updateSupplierRequest.getProductId() > 0) {
            supplier.setProduct(productService.findById(updateSupplierRequest.getProductId()));
        }
        supplier.setUpdatedBy(getCurrentUserEmail());
        supplier = saveSupplier(supplier);
        return new BaseResponse<>(200, true, "Supplier updated successfully", Util.mapToSupplierResponse(supplier));
    }

    @Override
    public BaseResponse<?> deleteSupplierBySupplierId(long supplierId) {
        deleteSupplier(supplierId);
        return new BaseResponse<>(200, true, "Supplier deleted successfully", null);
    }

    private void validateUpdateSupplierRequest(UpdateSupplierRequest updateSupplierRequest){
        if ((null == updateSupplierRequest.getSupplierName() || updateSupplierRequest.getSupplierName().isEmpty()) && (updateSupplierRequest.getProductId() <= 0)){
            throw new SupplierServiceException("Supplier name or product id is required");
        }
    }
    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsername();
        }

        return null;
    }
}
