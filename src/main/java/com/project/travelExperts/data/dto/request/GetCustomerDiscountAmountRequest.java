package com.project.travelExperts.data.dto.request;

import java.math.BigDecimal;

public class GetCustomerDiscountAmountRequest {
    private BigDecimal initialAmount;

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }
}
