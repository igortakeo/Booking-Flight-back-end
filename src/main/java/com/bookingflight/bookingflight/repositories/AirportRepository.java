package com.bookingflight.bookingflight.repositories;

import com.bookingflight.bookingflight.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    Airport findByName(String name);
}
