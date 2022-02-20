package com.bookingflight.bookingflight.repositories;

import com.bookingflight.bookingflight.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("select f " +
            "from Flight f " +
            "where f.source = :source " +
            "and f.target = :target " +
            "and f.date = :date " +
            "and f.airplane.id = :airplaneId " +
            "and f.airport.id = :airportId")
    Flight findSameFlight(String source,
                          String target,
                          LocalDateTime date,
                          Long airplaneId,
                          Long airportId);
}
