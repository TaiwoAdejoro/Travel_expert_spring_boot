package com.project.travelExperts.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class CreatePackageRequest {

    @NotEmpty(message = "Package name is required")
    private String name;
    @NotEmpty(message = "Description name is required")
    private String description;

//    @NotEmpty(message = "Supplier name is required")
//    private String supplierName;
    @NotEmpty(message = "Start date is required")
    private String startDate;
    @NotEmpty(message = "End date is required")
    private String endDate;
    @NotNull(message = "Package Base price is required")
    private BigDecimal basePrice;
    @NotNull(message = "Package Agency commission  is required")
    private BigDecimal agencyCommission;

//    private List<CreateProductRequest> products;


//    public String getSupplierName() {
//        return supplierName;
//    }
//
//    public void setSupplierName(String supplierName) {
//        this.supplierName = supplierName;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getAgencyCommission() {
        return agencyCommission;
    }

    public void setAgencyCommission(BigDecimal agencyCommission) {
        this.agencyCommission = agencyCommission;
    }
}
