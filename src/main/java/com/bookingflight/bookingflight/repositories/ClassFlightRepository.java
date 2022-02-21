package com.bookingflight.bookingflight.repositories;

import com.bookingflight.bookingflight.domain.ClassFlight;
import com.bookingflight.bookingflight.domain.ClassFlightId;
import com.bookingflight.bookingflight.domain.ClassTravelEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassFlightRepository extends JpaRepository<ClassFlight, ClassFlightId> {

    @Query("select cf " +
            "from ClassFlight cf " +
            "where cf.classTravel = :classTravel and cf.flight.id = :id")
    Optional<ClassFlight> findByClassTravelAndFlightId(ClassTravelEnum classTravel, Long id);

}