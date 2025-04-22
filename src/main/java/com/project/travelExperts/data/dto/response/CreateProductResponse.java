package com.project.travelExperts.data.dto.response;

public class CreateProductResponse extends BaseResponse<ProductResponseDto>{

    public CreateProductResponse(int statusCode, boolean success, String message, ProductResponseDto data) {
        super(statusCode, success, message, data);
    }

    public CreateProductResponse(boolean success, String message) {
        super(success, message);
    }

    public CreateProductResponse(boolean success, ProductResponseDto data) {
        super(success, data);
    }

    public CreateProductResponse(int statusCode, boolean success, String message) {
        super(statusCode, success, message);
    }
}
