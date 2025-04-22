package com.project.travelExperts.data.dto.response;

public class FetchAdminDetailsResponse extends BaseResponse<AdminResponseDto>{
    public FetchAdminDetailsResponse(int statusCode, boolean success, String message, AdminResponseDto data) {
        super(statusCode, success, message, data);
    }

    public FetchAdminDetailsResponse(boolean success, String message) {
        super(success, message);
    }

    public FetchAdminDetailsResponse(boolean success, AdminResponseDto data) {
        super(success, data);
    }

    public FetchAdminDetailsResponse(int statusCode, boolean success, String message) {
        super(statusCode, success, message);
    }
}
