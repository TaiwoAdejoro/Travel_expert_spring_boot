package com.project.travelExperts.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateSupplierRequest {
    @NotEmpty(message = "Supplier name is required")
    private String supplierName;

    @NotNull(message = "Product IDs are required")
    private long productId;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }
}
