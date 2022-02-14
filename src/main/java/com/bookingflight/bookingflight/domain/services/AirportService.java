package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Airport;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport findById(Long id) {
        Optional<Airport> obj = airportRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found with Id = " + id));
    }

    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    public Airport create(Airport obj) {
        Airport airport = airportRepository.findByName(obj.getName());

        if(airport != null){
            throw new ObjectAlreadyExistException("Object already exist (change name)");
        }

        airport.setId(null);
        return airportRepository.save(airport);
    }
}
