package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Flight;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight findById(Long id) {
        Optional<Flight> obj = flightRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found with Id = " + id));
    }

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public Flight create(Flight obj) {

        Flight flight = flightRepository.findSameFlight(
                obj.getSource(),
                obj.getTarget(),
                obj.getDate(),
                obj.getAirplane().getId(),
                obj.getAirport().getId()
        );

        if(flight != null){
            throw new ObjectAlreadyExistException("Create failed: FLight already exist");
        }

        obj.setId(null);
        return flightRepository.save(obj);
    }

    public Flight update(Long id, Flight obj) {
        Flight flight = findById(id);

        Flight flightVerify = flightRepository.findSameFlight(
                obj.getSource(),
                obj.getTarget(),
                obj.getDate(),
                obj.getAirplane().getId(),
                obj.getAirport().getId()
        );

        if(flightVerify != null && !flight.getId().equals(flightVerify.getId())){
            throw new ObjectAlreadyExistException("Update failed: Flight already exist");
        }

        flight.setAirport(obj.getAirport());
        flight.setAirplane(obj.getAirplane());
        flight.setSource(obj.getSource());
        flight.setTarget(obj.getTarget());
        flight.setDate(obj.getDate());

        return flightRepository.save(flight);
    }

    public void delete(Long id) {
        Flight flight = findById(id);
        flightRepository.delete(flight);
    }
}