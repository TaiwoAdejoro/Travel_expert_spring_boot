package com.project.travelExperts.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateAgentManagerRequest  {
    @NotBlank(message = "Username or email cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Agent manager name cannot be blank")
    private String agentManagerName;

    public String getAgentManagerName() {
        return agentManagerName;
    }

    public void setAgentManagerName(String agentManagerName) {
        this.agentManagerName = agentManagerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
