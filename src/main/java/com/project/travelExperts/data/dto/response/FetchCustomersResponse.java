package com.project.travelExperts.data.dto.response;

import java.util.List;

public class FetchCustomersResponse extends BaseResponse<List<CustomerResponseDto>> {
    public FetchCustomersResponse(int statusCode, boolean success, String message, List<CustomerResponseDto> data) {
        super(statusCode, success, message, data);
    }

    public FetchCustomersResponse(boolean success, String message) {
        super(success, message);
    }

    public FetchCustomersResponse(boolean success, List<CustomerResponseDto> data) {
        super(success, data);
    }

    public FetchCustomersResponse(int statusCode, boolean success, String message) {
        super(statusCode, success, message);
    }
}
