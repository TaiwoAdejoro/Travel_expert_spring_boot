package com.project.travelExperts.data.dto.response;

import com.project.travelExperts.data.enums.Role;

public class AgentResponseDto {

    private long id;
    private String firstName;

    private String agentMiddleName;
    private String agentLastName;
    private String agentBusPhone;
    private String agentEmail;

    private String agentPosition;

    private boolean isEnabled;

    private boolean isLocked;

    private Role role;

    private String agentCode;

    private long point;

    private AdminResponseDto agentManager;

    public AdminResponseDto getAgentManager() {
        return agentManager;
    }

    public void setAgentManager(AdminResponseDto agentManager) {
        this.agentManager = agentManager;
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

    public String getAgentBusPhone() {
        return agentBusPhone;
    }

    public void setAgentBusPhone(String agentBusPhone) {
        this.agentBusPhone = agentBusPhone;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public String getAgentPosition() {
        return agentPosition;
    }

    public void setAgentPosition(String agentPosition) {
        this.agentPosition = agentPosition;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }
}
