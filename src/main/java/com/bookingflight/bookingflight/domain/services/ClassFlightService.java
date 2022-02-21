package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.ClassFlight;
import com.bookingflight.bookingflight.domain.ClassTravelEnum;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.ClassFlightRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassFlightService {

    private final ClassFlightRepository classFlightRepository;

    public ClassFlightService(ClassFlightRepository classFlightRepository) {
        this.classFlightRepository = classFlightRepository;
    }

    public ClassFlight findByClassTravelAndFlightId(ClassTravelEnum classTravel, Long id) {
        Optional<ClassFlight> obj = classFlightRepository.findByClassTravelAndFlightId(classTravel, id);

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found with Class Travel = " + classTravel + " and Flight Id = " + id));
    }
}
