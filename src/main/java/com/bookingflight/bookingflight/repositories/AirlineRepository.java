package com.bookingflight.bookingflight.repositories;

import com.bookingflight.bookingflight.domain.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AirlineRepository extends JpaRepository<Airline, String> {

    Optional<Airline> findByCode(String code);

    void deleteByCode(String code);

    Airline findByName(String name);
}
