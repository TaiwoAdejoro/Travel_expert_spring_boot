package com.project.travelExperts.data.dto.response;

public class SupplierResponse extends BaseResponse<Object> {

    public SupplierResponse(int statusCode, boolean success, String message, Object data) {
        super(statusCode, success, message, data);
    }

    public SupplierResponse(boolean success, String message) {
        super(success, message);
    }

    public SupplierResponse(boolean success, Object data) {
        super(success, data);
    }

    public SupplierResponse(int statusCode, boolean success, String message) {
        super(statusCode, success, message);
    }
}
