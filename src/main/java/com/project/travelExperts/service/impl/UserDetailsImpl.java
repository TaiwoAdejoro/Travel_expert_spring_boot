package com.project.travelExperts.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.travelExperts.data.model.Admin;
import com.project.travelExperts.data.model.Agent;
import com.project.travelExperts.data.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl  implements UserDetails {

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String role;

    private boolean isEnabled;

    public UserDetailsImpl(Admin admin) {
        this.id = admin.getAdminId();
        this.username = admin.getUsername();
        this.password = admin.getPassword();
        this.role = admin.getRole().name();
        this.isEnabled = admin.isEnabled();
    }

    public UserDetailsImpl(Customer customer) {
        this.id = customer.getId();
        this.username = customer.getEmail();
        this.password = customer.getPassword();
        this.role = customer.getRole().name();
        this.isEnabled = customer.isEnabled();
    }
    public UserDetailsImpl(Agent agent) {
        this.id = agent.getAgentId();
        this.username = agent.getAgentEmail();
        this.password = agent.getPassword();
        this.role = agent.getAgentRole().name();
        this.isEnabled = agent.isEnabled();
    }
    public static UserDetails build(Admin admin) {
        return new UserDetailsImpl(admin);
    }

    public static UserDetails build(Customer customer) {
        return new UserDetailsImpl(customer);
    }

    public static UserDetails build(Agent agent) {
        return new UserDetailsImpl(agent);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
