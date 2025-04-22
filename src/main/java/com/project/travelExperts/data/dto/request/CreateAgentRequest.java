package com.project.travelExperts.data.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateAgentRequest {

    @NotBlank(message = "Firstname cannot be blank")
    private String firstName;
//    @NotBlank(message = "Middle name cannot be blank")
//    private String agentMiddleName;
    @NotBlank(message = "Last name cannot be blank")
    private String agentLastName;
//    @NotBlank(message = "PhoneNumber  cannot be blank")
//    private String agentBusPhone;
    @NotBlank(message = "Email cannot be blank")
    private String agentEmail;
//    @NotBlank(message = "Position cannot be blank")
//    private String agentPosition;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    public CreateAgentRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getAgentLastName() {
        return agentLastName;
    }

    public void setAgentLastName(String agentLastName) {
        this.agentLastName = agentLastName;
    }


    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
