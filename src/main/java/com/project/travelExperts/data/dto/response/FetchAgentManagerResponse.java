package com.project.travelExperts.data.dto.response;

public class FetchAgentManagerResponse extends BaseResponse<Object>{
    public FetchAgentManagerResponse(int statusCode, boolean success, String message, Object data) {
        super(statusCode, success, message, data);
    }

    public FetchAgentManagerResponse(boolean success, String message) {
        super(success, message);
    }

    public FetchAgentManagerResponse(boolean success, Object data) {
        super(success, data);
    }

    public FetchAgentManagerResponse(int statusCode, boolean success, String message) {
        super(statusCode, success, message);
    }
}
