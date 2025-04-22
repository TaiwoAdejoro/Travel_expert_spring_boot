package com.project.travelExperts.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class RegisterCustomerRequest {
    @NotBlank(message = "Email cannot be blank")
    @NotEmpty(message = "Email cannot be Empty")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Firstname cannot be blank")
    @NotEmpty(message = "Firstname cannot be empty")
    private String firstName;
    @NotBlank(message = "Lastname cannot be blank")
    @NotEmpty(message = "Lastname cannot be empty")
    private String lastName;
//    @NotBlank(message = "Bus phone cannot be blank")
//    @NotEmpty(message = "Bus phone cannot be empty")
//    private String busPhone;
//
//    @NotBlank(message = "Country cannot be blank")
//    @NotEmpty(message = "Country cannot be empty")
//    private String country;

//    @NotBlank(message = "City cannot be blank")
//    @NotEmpty(message = "City cannot be empty")
//    private String city;
//    @NotBlank(message = "Address cannot be blank")
//    @NotEmpty(message = "Address cannot be empty")
//    private String address;

//    private String homePhone;
//    @NotBlank(message = "Postal code cannot be blank")
//    @NotEmpty(message = "Postal code cannot be empty")
//    private String postalCode;
//    @NotBlank(message = "Province cannot be blank")
//    @NotEmpty(message = "Province cannot be empty")
//    private String province;

//    @NotBlank(message = "Agent code cannot be blank")
//    @NotEmpty(message = "Agent code cannot be empty")
    private String agentCode;

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public RegisterCustomerRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public String getBusPhone() {
//        return busPhone;
//    }
//
//    public void setBusPhone(String busPhone) {
//        this.busPhone = busPhone;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getHomePhone() {
//        return homePhone;
//    }
//
//    public void setHomePhone(String homePhone) {
//        this.homePhone = homePhone;
//    }
//
//    public String getPostalCode() {
//        return postalCode;
//    }
//
//    public void setPostalCode(String postalCode) {
//        this.postalCode = postalCode;
//    }
//
//    public String getProvince() {
//        return province;
//    }
//
//    public void setProvince(String province) {
//        this.province = province;
//    }
}
