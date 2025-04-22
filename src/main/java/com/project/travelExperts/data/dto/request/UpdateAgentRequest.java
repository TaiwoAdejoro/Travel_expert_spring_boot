package com.project.travelExperts.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateAgentRequest {
    private String agentBusPhone;
    private String firstName;
    private String agentMiddleName;
    private String agentLastName;

    private String agentPosition;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAgentBusPhone() {
        return agentBusPhone;
    }

    public void setAgentBusPhone(String agentBusPhone) {
        this.agentBusPhone = agentBusPhone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAgentMiddleName() {
        return agentMiddleName;
    }

    public void setAgentMiddleName(String agentMiddleName) {
        this.agentMiddleName = agentMiddleName;
    }

    public String getAgentLastName() {
        return agentLastName;
    }

    public void setAgentLastName(String agentLastName) {
        this.agentLastName = agentLastName;
    }

    public String getAgentPosition() {
        return agentPosition;
    }

    public void setAgentPosition(String agentPosition) {
        this.agentPosition = agentPosition;
    }
}
