package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.request.UpdateCustomerRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.data.dto.response.FetchCustomerDetailsResponse;
import com.project.travelExperts.data.dto.response.FetchCustomersResponse;
import com.project.travelExperts.data.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Customer getCustomerById(long customerId);

    Customer getCustomerByEmail(String email);

    Customer getCustomerByEmailForLogin(String email);

    boolean isCustomerExists(String email);

    Customer findTopBy();

    List<Customer> fetchCustomers(int page, int size);

    List<Customer> fetchCustomersByAgentId(long agentId, int page, int size);

    FetchCustomerDetailsResponse fetchCustomerDetails();

    BaseResponse updateCustomerDetails(long customerId, UpdateCustomerRequest updateCustomerRequest);

    boolean existsByUsername(String username);
}
