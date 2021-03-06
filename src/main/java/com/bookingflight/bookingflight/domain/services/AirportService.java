package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Airline;
import com.bookingflight.bookingflight.domain.Airport;
import com.bookingflight.bookingflight.domain.Flight;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.AirlineRepository;
import com.bookingflight.bookingflight.repositories.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    private final AirlineRepository airlineRepository;

    public AirportService(AirportRepository airportRepository, AirlineRepository airlineRepository) {
        this.airportRepository = airportRepository;
        this.airlineRepository = airlineRepository;
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

        obj.setId(null);
        return airportRepository.save(obj);
    }

    public Airport update(Long id, Airport obj) {
        Airport airport = findById(id);

        Airport airportVerify = airportRepository.findByName(obj.getName());

        if(airportVerify != null && !airport.getId().equals(airportVerify.getId())){
            throw new ObjectAlreadyExistException("Object already exist (change name)");
        }

        airport.setName(obj.getName());
        airport.setStreet(obj.getStreet());
        airport.setNumber(obj.getNumber());
        airport.setCep(obj.getCep());
        airport.setCity(obj.getCity());

        return airportRepository.save(airport);
    }

    public void delete(Long id) {
        Airport airport = findById(id);

        if(!airport.getAirlines().isEmpty()) {
            for (Airline airline : airport.getAirlines()) {
                airline.getAirports().removeIf(a -> a.getId().equals(id));
            }
        }

        if(!airport.getFlights().isEmpty()) {
            for (Flight flight : airport.getFlights()) {
                flight.setAirport(null);
            }
        }

        airportRepository.deleteById(id);
    }

    public List<Airline> findAirlines(Long id) {
        Airport airport = findById(id);
        return airport.getAirlines();
    }

    public Airport addAirline(Long id, String code) {
        Airport airport = findById(id);

        Optional<Airline> airline = airlineRepository.findByCode(code);

        if(!airline.isPresent()){
            throw new ObjectNotFoundException("Object not found");
        }

        if(airport.getAirlines().contains(airline.get())){
            throw new ObjectAlreadyExistException("Object already exist in list");
        }

        airport.getAirlines().add(airline.get());

        return airportRepository.save(airport);
    }
}
