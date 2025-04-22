package com.project.travelExperts.data.dto.request;

import com.project.travelExperts.data.enums.TripType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class CreateBookingRequest {

    @NotNull(message = "Customer ID cannot be null")
    private long packageId;

    private List<Long> productIds;

    @NotNull(message = "Amount with discount cannot be null")
    private BigDecimal amountWithDiscount;
    @NotNull(message = "Amount without discount cannot be null")
    private BigDecimal amountWithoutDiscount;

    private String transactionReference;

    public BigDecimal getAmountWithDiscount() {
        return amountWithDiscount;
    }

    public void setAmountWithDiscount(BigDecimal amountWithDiscount) {
        this.amountWithDiscount = amountWithDiscount;
    }

    public BigDecimal getAmountWithoutDiscount() {
        return amountWithoutDiscount;
    }

    public void setAmountWithoutDiscount(BigDecimal amountWithoutDiscount) {
        this.amountWithoutDiscount = amountWithoutDiscount;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

}
