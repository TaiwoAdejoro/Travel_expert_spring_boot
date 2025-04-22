package com.project.travelExperts.data.dto.response;

public class CreateAgentResponse extends BaseResponse<Object> {


    public CreateAgentResponse(int statusCode, boolean success, String message, Object data) {
        super(statusCode, success, message, data);
    }

    public CreateAgentResponse(boolean success, String message) {
        super(success, message);
    }

    public CreateAgentResponse(boolean success, Object data) {
        super(success, data);
    }
}
