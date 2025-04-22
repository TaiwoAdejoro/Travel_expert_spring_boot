package com.project.travelExperts.service.impl;

import com.project.travelExperts.data.dto.request.CreateBookingRequest;
import com.project.travelExperts.data.dto.request.GetCustomerDiscountAmountRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.data.dto.response.GetCustomerDiscountAmountResponse;
import com.project.travelExperts.data.dto.response.PayPalCaptureOrderResponse;
import com.project.travelExperts.data.dto.response.PaystcakVerifyTransactionResponse;
import com.project.travelExperts.data.enums.BookingStatus;
import com.project.travelExperts.data.enums.CustomerType;
import com.project.travelExperts.data.enums.TripType;
import com.project.travelExperts.data.model.*;
import com.project.travelExperts.data.model.Package;
import com.project.travelExperts.data.repository.BookingRepository;
import com.project.travelExperts.exception.BookingServiceException;
import com.project.travelExperts.service.AgentService;
import com.project.travelExperts.service.BookingService;
import com.project.travelExperts.service.PayPalService;
import com.project.travelExperts.service.PaystackService;
import com.project.travelExperts.utils.Util;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private PackageServiceImpl packageService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private AgentService agentService;

    @Autowired
    PaystackService paystackService;

    @Autowired
    PayPalService payPalService;
    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findBookingById(long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(()-> new BookingServiceException("Booking not found with id: " + bookingId));
    }

    @Override
    public List<Booking> findBookingByCustomerId(long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public BaseResponse<?> findCustomerBookingByCustomerId(long customerId) {
        List<Booking> customersBookings = findBookingByCustomerId(customerId);
        if (customersBookings.isEmpty()){
            return new BaseResponse<>(200,true,"No booking found for this customer", null);
        }
        return new BaseResponse<>(200,true,"Booking found successfully", customersBookings.stream().map(Util::mapToBookingResponse).toList());
    }

    @Override
    @Transactional
    public BaseResponse<?> createBooking(CreateBookingRequest createBookingRequest) {
        Customer customer =  customerService.getCustomerByEmail(getCurrentUserEmail());
        Package bookingPackage =  packageService.getPackageByID(createBookingRequest.getPackageId());
        Booking booking =  new Booking();
        booking.setBookingNumber(generateBookingNumber());
        booking.setBookingDate(new Date());
        booking.setaPackage(bookingPackage);
        if (null == createBookingRequest.getTransactionReference() ||createBookingRequest.getTransactionReference().isEmpty()){
            booking.setTransactionReference("");
            booking.setStatus(BookingStatus.RESERVED);
        } else {
//            PaystcakVerifyTransactionResponse response = paystackService.verifyTransaction(createBookingRequest.getTransactionReference());
            PayPalCaptureOrderResponse payPalCaptureOrderResponse = payPalService.captureOrder(createBookingRequest.getTransactionReference());
            if (payPalCaptureOrderResponse.getStatus().equalsIgnoreCase("COMPLETED")) {
                booking.setStatus(BookingStatus.PAID);
            } else if (payPalCaptureOrderResponse.getStatus().equalsIgnoreCase("CANCELED")) {
                booking.setStatus(BookingStatus.CANCELED);
            }else {
                booking.setTransactionReference(createBookingRequest.getTransactionReference());
                booking.setStatus(BookingStatus.PROCESSING);
            }

        }

        booking.setTotalAmount(createBookingRequest.getAmountWithoutDiscount());
        booking.setTotalAmountAfterDiscount(createBookingRequest.getAmountWithDiscount());
        booking.setCustomer(customer);
        customer.setPoint(customer.getPoint() + calculateCustomerPoint(createBookingRequest.getAmountWithDiscount()));
        customer.setTotalAmountSpent(customer.getTotalAmountSpent().add(createBookingRequest.getAmountWithDiscount()));
        if (customer.getTotalAmountSpent().doubleValue()>= 5000.0 &&customer.getTotalAmountSpent().doubleValue()<= 20000.0  ){
            customer.setCustomerType(CustomerType.BRONZE);
        }

        if (customer.getTotalAmountSpent().doubleValue()>= 20000.0){
            customer.setCustomerType(CustomerType.PLATINUM);
        }
        Agent agent  = customer.getAgent();
        agent.setPoint(agent.getPoint() + calculateCustomerPoint(createBookingRequest.getAmountWithDiscount()));

        booking.setProducts(getAllBookingProducts(createBookingRequest.getProductIds()));
        booking = saveBooking(booking);

        agentService.saveAgent(agent);
        customerService.saveCustomer(customer);

        return new BaseResponse<>(200,true,"Booking created successfully", Util.mapToBookingResponse(booking));
    }

    @Override
    public BaseResponse<?> getCustomerDiscountAmount(GetCustomerDiscountAmountRequest request) {
        Customer customer = customerService.getCustomerByEmail(getCurrentUserEmail());
        BigDecimal initialAmount = request.getInitialAmount();
        BigDecimal discountAmount = BigDecimal.ZERO;

        if (customer.getPoint() >= 5000 && customer.getPoint() < 20000 && customer.getTotalAmountSpent().compareTo(BigDecimal.valueOf(10000)) <= 0) {
            discountAmount = initialAmount.multiply(BigDecimal.valueOf(0.15)); // 15% discount
        } else if (customer.getPoint() >= 20000) {
            discountAmount = initialAmount.multiply(BigDecimal.valueOf(0.10)); // 10% discount
        }

        BigDecimal totalAmountAfterDiscount = initialAmount.subtract(discountAmount);

        GetCustomerDiscountAmountResponse response = new GetCustomerDiscountAmountResponse();
        response.setInitialAmount(initialAmount);
        response.setTotalAmountAfterDiscount(totalAmountAfterDiscount);

        return new BaseResponse<>(200, true, "Discount calculated successfully", response);
    }

    private long calculateCustomerPoint(BigDecimal amount){
        return amount.longValue();
    }

    private TripType getTripType(String tripType) {
        if (tripType.equalsIgnoreCase("business")) {
            return TripType.BUSINESS;
        } else if (tripType.equalsIgnoreCase("leisure")) {
            return TripType.LEISURE;
        } else {
            throw new BookingServiceException("Invalid trip type: " + tripType);
        }
    }

    private List<Product> getAllBookingProducts(List<Long> productIds){
        List<Product> products = new ArrayList<>();
        productIds.forEach(productId -> products.add(productService.findById(productId)));
        return products;
    }
    private String generateBookingNumber() {
        StringBuilder bookingNumber = new StringBuilder();
        bookingNumber.append("BKG-");
        for (int i = 0; i < 6; i++) {
            int randomDigit = (int) (Math.random() * 10);
            bookingNumber.append(randomDigit);
        }

        if (bookingRepository.findByBookingNumber(bookingNumber.toString()).isPresent()) {
            return generateBookingNumber();
        }
        return bookingNumber.toString();
    }


    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getUsername();
        }
        return null;
    }
    @Override
    public List<Booking> findByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    @Override
    public BaseResponse<?> getBookingById(long bookingId) {
        Booking booking = findBookingById(bookingId);
        return new BaseResponse<>(200, true, "Booking found successfully", Util.mapToBookingResponse(booking));
    }

}
