package com.bookingflight.bookingflight.repositories;

import com.bookingflight.bookingflight.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, String> {
    Optional<Passenger> findByCpf(String cpf);
}
