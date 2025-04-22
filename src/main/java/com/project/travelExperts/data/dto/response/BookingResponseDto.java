package com.project.travelExperts.data.dto.response;

import com.project.travelExperts.data.enums.BookingStatus;
import com.project.travelExperts.data.enums.TripType;
import com.project.travelExperts.data.model.Customer;
import com.project.travelExperts.data.model.Package;
import com.project.travelExperts.data.model.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BookingResponseDto {

    private long bookingId;
    private String bookingNumber;
    private TripType tripType;

    private BookingStatus status;

    private Date bookingDate;
    private double travelCount;
    private CustomerResponseDto customer;
    private PackageResponseDto aPackage;

    private String transactionReference;

    private List<ProductResponseDto> products;

    private BigDecimal totalAmount;
    private BigDecimal totalAmountAfterDiscount;

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTravelCount() {
        return travelCount;
    }

    public void setTravelCount(double travelCount) {
        this.travelCount = travelCount;
    }

    public CustomerResponseDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponseDto customer) {
        this.customer = customer;
    }

    public PackageResponseDto getaPackage() {
        return aPackage;
    }

    public void setaPackage(PackageResponseDto aPackage) {
        this.aPackage = aPackage;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public List<ProductResponseDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDto> products) {
        this.products = products;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmountAfterDiscount() {
        return totalAmountAfterDiscount;
    }

    public void setTotalAmountAfterDiscount(BigDecimal totalAmountAfterDiscount) {
        this.totalAmountAfterDiscount = totalAmountAfterDiscount;
    }
}
