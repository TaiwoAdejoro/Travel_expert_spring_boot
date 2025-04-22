package com.project.travelExperts.data.repository;

import com.project.travelExperts.data.enums.BookingStatus;
import com.project.travelExperts.data.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingNumber(String bookingNumber);

    List<Booking> findByCustomerId(long customerId);

    List<Booking> findByStatus(BookingStatus status);
}
