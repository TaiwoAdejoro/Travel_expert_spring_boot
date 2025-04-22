package com.project.travelExperts.service;

import com.project.travelExperts.data.dto.request.CreateBookingRequest;
import com.project.travelExperts.data.dto.request.GetCustomerDiscountAmountRequest;
import com.project.travelExperts.data.dto.response.BaseResponse;
import com.project.travelExperts.data.enums.BookingStatus;
import com.project.travelExperts.data.model.Booking;

import java.util.List;

public interface BookingService {
    Booking saveBooking(Booking booking);
    Booking findBookingById(long bookingId);

    List<Booking> findBookingByCustomerId(long customerId);
    BaseResponse<?> findCustomerBookingByCustomerId(long customerId);

    BaseResponse<?> createBooking(CreateBookingRequest createBookingRequest);
    BaseResponse<?> getCustomerDiscountAmount(GetCustomerDiscountAmountRequest request);

    List<Booking> findByStatus(BookingStatus status);

    BaseResponse<?> getBookingById(long bookingId);
}
