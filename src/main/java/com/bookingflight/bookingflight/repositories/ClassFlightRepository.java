package com.bookingflight.bookingflight.repositories;

import com.bookingflight.bookingflight.domain.ClassFlight;
import com.bookingflight.bookingflight.domain.ClassFlightId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassFlightRepository extends JpaRepository<ClassFlight, ClassFlightId> {
}