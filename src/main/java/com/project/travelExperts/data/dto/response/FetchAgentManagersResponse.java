package com.project.travelExperts.data.dto.response;

import java.util.List;

public class FetchAgentManagersResponse extends BaseResponse<List<AdminResponseDto>>{
    public FetchAgentManagersResponse(int statusCode, boolean success, String message, List<AdminResponseDto> data) {
        super(statusCode, success, message, data);
    }

    public FetchAgentManagersResponse(boolean success, String message) {
        super(success, message);
    }

    public FetchAgentManagersResponse(boolean success, List<AdminResponseDto> data) {
        super(success, data);
    }
}
