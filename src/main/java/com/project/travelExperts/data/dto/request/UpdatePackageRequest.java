package com.project.travelExperts.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UpdatePackageRequest {
//    @NotEmpty(message = "Package name is required")
    private String name;

//    @NotEmpty(message = "Package description is required")

    private String description;
//    @NotNull(message = "Package base price is required")
    private BigDecimal basePrice;
//    @NotNull(message = "Package agency commission is required")
    private BigDecimal agencyCommission;

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
