package com.project.travelExperts.utils;

import com.project.travelExperts.data.dto.response.PayPalCaptureOrderResponse;
import com.project.travelExperts.data.dto.response.PaystcakVerifyTransactionResponse;
import com.project.travelExperts.data.enums.BookingStatus;
import com.project.travelExperts.data.model.Booking;
import com.project.travelExperts.data.repository.BookingRepository;
import com.project.travelExperts.service.BookingService;
import com.project.travelExperts.service.PayPalService;
import com.project.travelExperts.service.PaystackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class BookingJobs {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PayPalService payPalService;


    @Scheduled(cron = "0 0 0 * * *") // Runs at midnight daily
    public void cancelExpiredReservedBookings() {
        List<Booking> reservedBookings = bookingService.findByStatus(BookingStatus.RESERVED);
        Date now = new Date();

        for (Booking booking : reservedBookings) {
            long timeDifference = now.getTime() - booking.getBookingDate().getTime();
            if (timeDifference > 24 * 60 * 60 * 1000) { // 24 hours in milliseconds
                booking.setStatus(BookingStatus.CANCELED);
                bookingService.saveBooking(booking);
            }
        }
    }

//     Job to verify payment for processing bookings
@Scheduled(fixedRate = 60000) // Runs every 1 minute
    public void verifyProcessingBookings() {
        List<Booking> processingBookings = bookingService.findByStatus(BookingStatus.PROCESSING);

        for (Booking booking : processingBookings) {
            PayPalCaptureOrderResponse response = payPalService.captureOrder(booking.getTransactionReference());
            if (response.getStatus().equalsIgnoreCase("COMPLETED")) {
                booking.setStatus(BookingStatus.PAID);
            } else if (response.getStatus().equalsIgnoreCase("FAILED")) {
                booking.setStatus(BookingStatus.CANCELED);
            }
            bookingService.saveBooking(booking);
        }
    }
}
