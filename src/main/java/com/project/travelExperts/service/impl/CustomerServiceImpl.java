package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.dto.request.UpdateCustomerRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.data.dto.response.CustomerResponseDto;
import com.project.travelExperts.data.dto.response.FetchCustomerDetailsResponse;
import com.project.travelExperts.data.dto.response.FetchCustomersResponse;
import com.project.travelExperts.data.model.Customer;
import com.project.travelExperts.data.repository.CustomerRepository;
import com.project.travelExperts.exception.CustomerServiceException;
import com.project.travelExperts.service.CustomerService;
import com.project.travelExperts.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        throw new CustomerServiceException("Customer not found with id: " + customerId);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        throw new CustomerServiceException("Customer not found with email: " + email);
    }

    @Override
    public Customer getCustomerByEmailForLogin(String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        return optionalCustomer.orElse(null);

    }

    @Override
    public boolean isCustomerExists(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public Customer findTopBy() {
        return customerRepository.findTopByOrderByCreatedAtDesc();
    }

    @Override
    public List<Customer> fetchCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return customerPage.getContent();
    }

    @Override
    public List<Customer> fetchCustomersByAgentId(long agentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAllByAgentAgentId(pageable, agentId);
        return customerPage.getContent();
    }

    @Override
    public FetchCustomerDetailsResponse fetchCustomerDetails() {
        String currentUserEmail = getCurrentUserEmail();
        Customer customer =  getCustomerByEmail(currentUserEmail);
        return new FetchCustomerDetailsResponse(200, true,"Customer details fetched successfully", Util.mapToCustomerResponseForDetails(customer));
    }

    @Override
    public BaseResponse<CustomerResponseDto> updateCustomerDetails(long customerId, UpdateCustomerRequest updateCustomerRequest) {
        validateUpdateCustomerRequest(updateCustomerRequest);
        Customer customer = getCustomerById(customerId);
        if (null != updateCustomerRequest.getFirstName() && !updateCustomerRequest.getFirstName().isBlank()){
            customer.setFirstName(updateCustomerRequest.getFirstName());
        }
        if (null != updateCustomerRequest.getLastName() && !updateCustomerRequest.getLastName().isBlank()){
            customer.setLastName(updateCustomerRequest.getLastName());
        }
        if (null != updateCustomerRequest.getBusPhone() && !updateCustomerRequest.getBusPhone().isBlank()){
            customer.setBusPhone(updateCustomerRequest.getBusPhone());
        }
        if (null != updateCustomerRequest.getCountry() && !updateCustomerRequest.getCountry().isBlank()){
            customer.setCountry(updateCustomerRequest.getCountry());
        }
        if (null != updateCustomerRequest.getCity() && !updateCustomerRequest.getCity().isBlank()){
            customer.setCity(updateCustomerRequest.getCity());
        }
        if (null != updateCustomerRequest.getAddress() && !updateCustomerRequest.getAddress().isBlank()){
            customer.setAddress(updateCustomerRequest.getAddress());
        }
        if (null != updateCustomerRequest.getHomePhone() && !updateCustomerRequest.getHomePhone().isBlank()){
            customer.setHomePhone(updateCustomerRequest.getHomePhone());
        }
        if (null != updateCustomerRequest.getPostalCode() && !updateCustomerRequest.getPostalCode().isBlank()){
            customer.setPostalCode(updateCustomerRequest.getPostalCode());
        }
        if (null != updateCustomerRequest.getProvince() && !updateCustomerRequest.getProvince().isBlank()){
            customer.setProvince(updateCustomerRequest.getProvince());
        }
        if (null != updateCustomerRequest.getImageUrl() && !updateCustomerRequest.getImageUrl().isBlank()){
            customer.setImageUrl(updateCustomerRequest.getImageUrl());
        }

        customer = customerRepository.save(customer);

        return new BaseResponse<>(200, true, "Customer updated successfully", Util.mapToCustomerResponseForDetails(customer));
    }

    @Override
    public boolean existsByUsername(String username) {
        return customerRepository.existsByEmail(username);
    }

    private void validateUpdateCustomerRequest(UpdateCustomerRequest updateCustomerRequest){
        if ((null == updateCustomerRequest.getFirstName() || updateCustomerRequest.getFirstName().isEmpty() || updateCustomerRequest.getFirstName().isBlank())
        &&(null ==  updateCustomerRequest.getLastName() || updateCustomerRequest.getLastName().isBlank() || updateCustomerRequest.getLastName().isEmpty())
        && (null == updateCustomerRequest.getBusPhone() || updateCustomerRequest.getBusPhone().isBlank() || updateCustomerRequest.getBusPhone().isEmpty())
        && (null == updateCustomerRequest.getCountry() || updateCustomerRequest.getCountry().isBlank() || updateCustomerRequest.getCountry().isEmpty())
        && (null == updateCustomerRequest.getCity() || updateCustomerRequest.getCity().isBlank() || updateCustomerRequest.getCity().isEmpty())
        && (null == updateCustomerRequest.getAddress() || updateCustomerRequest.getAddress().isBlank() || updateCustomerRequest.getAddress().isEmpty())
        && (null == updateCustomerRequest.getHomePhone() || updateCustomerRequest.getHomePhone().isBlank() || updateCustomerRequest.getHomePhone().isEmpty())
        && (null == updateCustomerRequest.getPostalCode() || updateCustomerRequest.getPostalCode().isBlank() || updateCustomerRequest.getPostalCode().isEmpty())
        && (null == updateCustomerRequest.getProvince() || updateCustomerRequest.getProvince().isBlank() || updateCustomerRequest.getProvince().isEmpty())
        && (null == updateCustomerRequest.getImageUrl() || updateCustomerRequest.getImageUrl().isBlank() || updateCustomerRequest.getImageUrl().isEmpty())){
            throw new CustomerServiceException("No fields to update");
        }

    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsername();
        }
        return null;
    }

}
