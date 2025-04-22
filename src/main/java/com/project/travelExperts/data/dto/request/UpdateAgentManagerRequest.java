package com.project.travelExperts.data.dto.request;

public class UpdateAgentManagerRequest {
    private  String agentManagerName;

    private String imageUrl;

    public String getAgentManagerName() {
        return agentManagerName;
    }

    public void setAgentManagerName(String agentManagerName) {
        this.agentManagerName = agentManagerName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
