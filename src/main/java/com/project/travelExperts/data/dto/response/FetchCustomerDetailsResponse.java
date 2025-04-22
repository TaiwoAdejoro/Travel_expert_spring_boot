package com.project.travelExperts.data.dto.response;

public class FetchCustomerDetailsResponse extends BaseResponse<CustomerResponseDto>{
    public FetchCustomerDetailsResponse(int statusCode, boolean success, String message, CustomerResponseDto data) {
        super(statusCode, success, message, data);
    }

    public FetchCustomerDetailsResponse(boolean success, String message) {
        super(success, message);
    }

    public FetchCustomerDetailsResponse(boolean success, CustomerResponseDto data) {
        super(success, data);
    }

    public FetchCustomerDetailsResponse(int statusCode, boolean success, String message) {
        super(statusCode, success, message);
    }
}
