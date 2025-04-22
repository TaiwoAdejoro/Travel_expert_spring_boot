package com.project.travelExperts.data.dto.response;

import com.project.travelExperts.data.enums.Role;

public class AdminResponseDto {
    private Long adminId;
    private String username;
    private Role role;

    private String imageUrl;
    private String agentManagerName;

    private String createdBy;

    private boolean isEnabled;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAgentManagerName() {
        return agentManagerName;
    }

    public void setAgentManagerName(String agentManagerName) {
        this.agentManagerName = agentManagerName;
    }
}
