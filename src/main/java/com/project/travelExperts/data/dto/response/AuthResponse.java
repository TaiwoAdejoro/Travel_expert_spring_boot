package com.project.travelExperts.data.dto.response;

public class AuthResponse extends BaseResponse<Object> {

    private String token;
    private String refreshToken;



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public AuthResponse(int statusCode, boolean success, String message, String jwt, String refreshToken) {
        super(statusCode, success, message);
        this.token = jwt;
        this.refreshToken = refreshToken;
    }

    public AuthResponse(int statusCode, boolean success, String message, Object data,String jwt, String refreshToken) {
        super(statusCode, success, message, data);
        this.token = jwt;
        this.refreshToken = refreshToken;
    }

    public AuthResponse(int statusCode, boolean success, String message, Object data) {
        super(statusCode, success, message, data);
    }

    public AuthResponse(boolean success, String message,String token, String refreshToken) {
        super(success, message);
        this.refreshToken =  refreshToken;
        this.token =  token;
    }


    public AuthResponse(int statusCode, boolean success, String message) {
        super(statusCode, success, message);
    }
}
