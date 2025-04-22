package com.project.travelExperts.data.model;

import com.project.travelExperts.data.enums.Role;
import jakarta.persistence.*;

//@Data
@Entity
@Table(name = "agents")
//@Setter @Getter
public class Agent {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "agent_id")
    private long agentId;
    private String firstName;

    private String agentMiddleName;
    private String agentLastName;
    private String agentBusPhone;
    private String agentEmail;
    private String agentPosition;
    private String password;

    private String agentCode;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Role agentRole;

    @JoinColumn(name = "agency_id")
    @ManyToOne
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Admin manager;

    private boolean isEnabled;

    private boolean isLocked;


    private int loginCount;

    private long point;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public Admin getManager() {
        return manager;
    }

    public void setManager(Admin manager) {
        this.manager = manager;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getAgentRole() {
        return agentRole;
    }

    public void setAgentRole(Role agentRole) {
        this.agentRole = agentRole;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }


    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
}
