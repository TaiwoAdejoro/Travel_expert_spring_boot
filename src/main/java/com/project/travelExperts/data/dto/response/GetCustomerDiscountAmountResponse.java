package com.project.travelExperts.data.dto.response;

import java.math.BigDecimal;

public class GetCustomerDiscountAmountResponse {
    private BigDecimal initialAmount;
    private BigDecimal totalAmountAfterDiscount;

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public BigDecimal getTotalAmountAfterDiscount() {
        return totalAmountAfterDiscount;
    }

    public void setTotalAmountAfterDiscount(BigDecimal totalAmountAfterDiscount) {
        this.totalAmountAfterDiscount = totalAmountAfterDiscount;
    }
}
