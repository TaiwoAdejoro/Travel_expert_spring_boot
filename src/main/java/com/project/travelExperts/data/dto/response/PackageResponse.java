package com.project.travelExperts.data.dto.response;

public class PackageResponse extends BaseResponse {
    public PackageResponse(int statusCode, boolean success, String message, Object data) {
        super(statusCode, success, message, data);
    }

    public PackageResponse(boolean success, String message) {
        super(success, message);
    }

    public PackageResponse(boolean success, Object data) {
        super(success, data);
    }

    public PackageResponse(int statusCode, boolean success, String message) {
        super(statusCode, success, message);
    }
}
