package com.bookingflight.bookingflight.repositories;

import com.bookingflight.bookingflight.domain.Booking;
import com.bookingflight.bookingflight.domain.BookingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, BookingId> {
}
