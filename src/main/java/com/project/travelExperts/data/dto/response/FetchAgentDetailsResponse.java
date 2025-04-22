package com.project.travelExperts.data.dto.response;

public class FetchAgentDetailsResponse extends BaseResponse<AgentResponseDto>{
    public FetchAgentDetailsResponse(int statusCode, boolean success, String message, AgentResponseDto data) {
        super(statusCode, success, message, data);
    }

    public FetchAgentDetailsResponse(boolean success, String message) {
        super(success, message);
    }

    public FetchAgentDetailsResponse(boolean success, AgentResponseDto data) {
        super(success, data);
    }

    public FetchAgentDetailsResponse(int statusCode, boolean success, String message) {
        super(statusCode, success, message);
    }
}
