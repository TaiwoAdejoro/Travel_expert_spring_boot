package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.request.CreateSupplierRequest;
import com.project.travelExperts.data.dto.request.UpdateSupplierRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.data.dto.response.SupplierResponse;
import com.project.travelExperts.data.model.Supplier;

public interface SupplierService {

    Supplier saveSupplier(Supplier supplier);
    void deleteSupplier(long supplierId);
    void deleteSupplier(Supplier supplier);

    SupplierResponse createNewSupplier(CreateSupplierRequest createSupplierRequest);

    SupplierResponse findAllSuppliers();

    SupplierResponse findSupplierById(long supplierId);
    Supplier findById(long supplierId);

    BaseResponse<?> updateSupplier(long supplierId, UpdateSupplierRequest createSupplierRequest);

    BaseResponse<?> deleteSupplierBySupplierId(long supplierId);
}
