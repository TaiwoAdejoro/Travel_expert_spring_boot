package com.project.travelExperts.data.dto.response;

public class CreateAgentManagerResponse extends BaseResponse {


    public CreateAgentManagerResponse(int statusCode, boolean success, String message, Object data) {
        super(statusCode, success, message, data);
    }

    public CreateAgentManagerResponse(boolean success, String message) {
        super(success, message);
    }

    public CreateAgentManagerResponse(int statusCode, boolean success, String message) {
        super(statusCode, success, message);
    }
}
