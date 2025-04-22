package com.project.travelExperts.utils;

import com.project.travelExperts.data.dto.response.*;
import com.project.travelExperts.data.model.*;
import com.project.travelExperts.data.model.Package;

public class Util {

    public static AgentResponseDto mapAgentToAgentResponse(Agent agent){
        AgentResponseDto agentResponseDto = new AgentResponseDto();
        agentResponseDto.setId(agent.getAgentId());
        agentResponseDto.setAgentCode(agent.getAgentCode());
        agentResponseDto.setFirstName(agent.getFirstName());
        agentResponseDto.setAgentLastName(agent.getAgentLastName());
        agentResponseDto.setAgentMiddleName(agent.getAgentMiddleName());
        agentResponseDto.setAgentPosition(agent.getAgentPosition());
        agentResponseDto.setAgentEmail(agent.getAgentEmail());
        agentResponseDto.setAgentBusPhone(agent.getAgentBusPhone());
        agentResponseDto.setAgentCode(agent.getAgentCode());
        agentResponseDto.setRole(agent.getAgentRole());
        agentResponseDto.setPoint(agent.getPoint());
        agentResponseDto.setEnabled(agent.isEnabled());
        agentResponseDto.setAgentManager(mapToAdminResponse(agent.getManager()));
        return agentResponseDto;
    }

    public static AgentResponseDto mapAgentToAgentResponseForDetials(Agent agent){
        AgentResponseDto agentResponseDto = new AgentResponseDto();
        agentResponseDto.setId(agent.getAgentId());
        agentResponseDto.setAgentCode(agent.getAgentCode());
        agentResponseDto.setFirstName(agent.getFirstName());
        agentResponseDto.setAgentLastName(agent.getAgentLastName());
        agentResponseDto.setAgentMiddleName(agent.getAgentMiddleName());
        agentResponseDto.setAgentPosition(agent.getAgentPosition());
        agentResponseDto.setAgentEmail(agent.getAgentEmail());
        agentResponseDto.setAgentBusPhone(agent.getAgentBusPhone());
        agentResponseDto.setAgentCode(agent.getAgentCode());
        agentResponseDto.setRole(agent.getAgentRole());
        agentResponseDto.setPoint(agent.getPoint());
        agentResponseDto.setEnabled(agent.isEnabled());
        agentResponseDto.setAgentManager(mapToAdminResponse(agent.getManager()));
        return agentResponseDto;
    }

    public static AdminResponseDto mapToAdminResponse(Admin admin){
        AdminResponseDto adminResponseDto = new AdminResponseDto();
        adminResponseDto.setAdminId(admin.getAdminId());
        adminResponseDto.setUsername(admin.getUsername());
        adminResponseDto.setRole(admin.getRole());
        adminResponseDto.setCreatedBy(admin.getCreatedBy());
        adminResponseDto.setImageUrl(admin.getImageUrl());
        adminResponseDto.setEnabled(admin.isEnabled());
        adminResponseDto.setAgentManagerName(admin.getAgentManagerName());
        return adminResponseDto;
    }

    public static AdminResponseDto mapToAdminResponseForDetails(Admin admin){
        AdminResponseDto adminResponseDto = new AdminResponseDto();
        adminResponseDto.setAdminId(admin.getAdminId());
        adminResponseDto.setUsername(admin.getUsername());
        adminResponseDto.setRole(admin.getRole());
        adminResponseDto.setCreatedBy(admin.getCreatedBy());
        adminResponseDto.setImageUrl(admin.getImageUrl());
        adminResponseDto.setEnabled(admin.isEnabled());
        adminResponseDto.setAgentManagerName(admin.getAgentManagerName());
        return adminResponseDto;
    }

    public static CustomerResponseDto mapToCustomerResponse(Customer customer){
        CustomerResponseDto customerResponseDto =  new CustomerResponseDto();
        customerResponseDto.setFirstName(customer.getFirstName());
        customerResponseDto.setLastName(customer.getLastName());
        customerResponseDto.setAddress(customer.getAddress());
        customerResponseDto.setBusPhone(customer.getBusPhone());
        customerResponseDto.setCity(customer.getCity());
        customerResponseDto.setCountry(customer.getCountry());
        customerResponseDto.setEmail(customer.getEmail());
        customerResponseDto.setHomePhone(customer.getHomePhone());
        customerResponseDto.setPostalCode(customer.getPostalCode());
        customerResponseDto.setProvince(customer.getProvince());
        customerResponseDto.setRole(customer.getRole());
        customerResponseDto.setImageUrl(customer.getImageUrl());
        customerResponseDto.setCustomerType(customer.getCustomerType());
        customerResponseDto.setEnabled(customerResponseDto.isEnabled());
        customerResponseDto.setAgent(mapAgentToAgentResponse(customer.getAgent()));
        return customerResponseDto;
    }

    public static CustomerResponseDto mapToCustomerResponseForDetails(Customer customer){
        CustomerResponseDto customerResponseDto =  new CustomerResponseDto();
        customerResponseDto.setId(customer.getId());
        customerResponseDto.setFirstName(customer.getFirstName());
        customerResponseDto.setLastName(customer.getLastName());
        customerResponseDto.setAddress(customer.getAddress());
        customerResponseDto.setBusPhone(customer.getBusPhone());
        customerResponseDto.setCity(customer.getCity());
        customerResponseDto.setCountry(customer.getCountry());
        customerResponseDto.setEmail(customer.getEmail());
        customerResponseDto.setHomePhone(customer.getHomePhone());
        customerResponseDto.setPostalCode(customer.getPostalCode());
        customerResponseDto.setProvince(customer.getProvince());
        customerResponseDto.setRole(customer.getRole());
        customerResponseDto.setCustomerType(customer.getCustomerType());
        customerResponseDto.setImageUrl(customer.getImageUrl());
        customerResponseDto.setEnabled(customerResponseDto.isEnabled());
        customerResponseDto.setAgent(mapAgentToAgentResponse(customer.getAgent()));
        return customerResponseDto;
    }

    public static PackageResponseDto mapToPackageResponse(Package p){
        PackageResponseDto packageResponse = new PackageResponseDto();
        packageResponse.setId(p.getId());
        packageResponse.setName(p.getName());
        packageResponse.setDescription(p.getDescription());
        packageResponse.setBasePrice(p.getBasePrice());
        packageResponse.setAgencyCommission(p.getAgencyCommission());
        packageResponse.setStartDate(p.getStartDate());
        packageResponse.setEndDate(p.getEndDate());
        packageResponse.setCreatedBy(p.getCreatedBy());
        packageResponse.setReviews(null!= p.getReviews()||! p.getReviews().isEmpty()?p.getReviews().stream().map(Util::mapToReviewResponse).toList():null);
        packageResponse.setStars(p.getAverageRatings());
//        packageResponse.setProducts(p.getProducts().stream().map(Util::mapToProductResponse).toList());
        return packageResponse;
    }

    public static ReviewResponseDto  mapToReviewResponse(Review review){
        ReviewResponseDto reviewResponseDto =  new ReviewResponseDto();
        reviewResponseDto.setContent(review.getContent());
        reviewResponseDto.setCreatedBy(review.getCreatedBy());
        reviewResponseDto.setId(review.getId());
        return reviewResponseDto;
    }

    public static ProductResponseDto mapToProductResponse(Product product){
        ProductResponseDto productResponse = new ProductResponseDto();
        productResponse.setProductId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        productResponse.setProductCost(product.getProductCost());
        productResponse.setAvailable(product.isAvailable());
        productResponse.setSupplierName(product.getProductSupplier()== null?  "":product.getProductSupplier().getName());
        return productResponse;
    }

    public static SupplierResponseDto mapToSupplierResponse(Supplier supplier){
        SupplierResponseDto supplierResponseDto =  new SupplierResponseDto();
        supplierResponseDto.setId(supplier.getId());
        supplierResponseDto.setName(supplier.getName());
        supplierResponseDto.setProduct(mapToProductResponse(supplier.getProduct()));
        supplierResponseDto.setCreatedBy(supplierResponseDto.getCreatedBy());
        return supplierResponseDto;
    }

    public static  BookingResponseDto mapToBookingResponse(Booking booking){
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        bookingResponseDto.setBookingId(booking.getBookingId());
        bookingResponseDto.setBookingNumber(booking.getBookingNumber());
        bookingResponseDto.setTripType(booking.getTripType());
        bookingResponseDto.setStatus(booking.getStatus());
        bookingResponseDto.setBookingDate(booking.getBookingDate());
        bookingResponseDto.setTravelCount(booking.getTravelCount());
        bookingResponseDto.setCustomer(mapToCustomerResponseForDetails(booking.getCustomer()));
        bookingResponseDto.setaPackage(mapToPackageResponse(booking.getaPackage()));
        bookingResponseDto.setTransactionReference(booking.getTransactionReference());
        bookingResponseDto.setProducts(booking.getProducts().stream().map(Util::mapToProductResponse).toList());
        bookingResponseDto.setTotalAmount(booking.getTotalAmount());
        bookingResponseDto.setTotalAmountAfterDiscount(booking.getTotalAmountAfterDiscount());
        return bookingResponseDto;
    }
}
