package com.project.travelExperts.controller;

import com.project.travelExperts.data.dto.request.CreateBookingRequest;
import com.project.travelExperts.data.dto.request.GetCustomerDiscountAmountRequest;
import com.project.travelExperts.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody @Valid CreateBookingRequest createBookingRequest){
        return ResponseEntity.ok(bookingService.createBooking(createBookingRequest));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCustomerBookings(@PathVariable @Valid @NotNull(message = "Customer Id is required") long customerId){
        return ResponseEntity.ok(bookingService.findCustomerBookingByCustomerId(customerId));
    }

    @GetMapping("/discount/{initialAmount}")
    public  ResponseEntity<?> getCustomerDiscountAmount(@PathVariable @Valid @NotNull(message = "booking is required") BigDecimal initialAmount){
        GetCustomerDiscountAmountRequest request = new GetCustomerDiscountAmountRequest();
        request.setInitialAmount(initialAmount);
        return ResponseEntity.ok(bookingService.getCustomerDiscountAmount(request));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable @Valid @NotNull(message = "Booking Id is required") long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }
}
