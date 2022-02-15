package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Airline;
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

        obj.setId(null);
        return airportRepository.save(obj);
    }

    public Airport update(Long id, Airport obj) {
        Airport airport = findById(id);

        Airport airportVerify = airportRepository.findByName(obj.getName());

        if(airport.getId() != airportVerify.getId()){
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
        findById(id);
        airportRepository.deleteById(id);
    }

    public List<Airline> findAirlines(Long id) {
        Airport airport = findById(id);
        return airport.getAirlines();
    }

    public Airport addAirline(Long id, Airline obj) {
        Airport airport = findById(id);
        airport.getAirlines().add(obj);

        return airportRepository.save(airport);
    }
}
