package com.project.travelExperts.data.dto.response;

import java.util.List;

public class FetchAgentsResponse extends BaseResponse<List<AgentResponseDto>> {

    public FetchAgentsResponse(int statusCode, boolean success, String message, List<AgentResponseDto> data) {
        super(statusCode, success, message, data);
    }

    public FetchAgentsResponse(boolean success, String message) {
        super(success, message);
    }

    public FetchAgentsResponse(boolean success, List<AgentResponseDto> data) {
        super(success, data);
    }

}
