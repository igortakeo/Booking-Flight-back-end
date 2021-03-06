package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Airline;
import com.bookingflight.bookingflight.domain.Airplane;
import com.bookingflight.bookingflight.domain.Airport;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.AirlineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public Airline findByCode(String code) {
        Optional<Airline> obj = airlineRepository.findByCode(code);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found with code = " + code));
    }

    public List<Airline> findAll(){
        return airlineRepository.findAll();
    }

    public Airline create(Airline obj) {
        Optional<Airline> airline = airlineRepository.findByCode(obj.getCode());

        if(airline.isPresent()){
            throw new ObjectAlreadyExistException("Object already exist");
        }

        return airlineRepository.save(obj);
    }

    public Airline update(String code, Airline obj) {
        Airline newAirline = findByCode(code);

        Airline airlineVerify = airlineRepository.findByName(obj.getName());

        if(airlineVerify != null && !newAirline.getCode().equals(airlineVerify.getCode())){
            throw new ObjectAlreadyExistException("Object already exist (change name)");
        }

        newAirline.setName(obj. getName());
        newAirline.setNumberPlanes(obj.getNumberPlanes());
        newAirline.setEmail(obj.getEmail());
        newAirline.setTelephone(obj.getTelephone());

        return airlineRepository.save(newAirline);
    }

    public void delete(String code) {
        Airline airline = findByCode(code);

        if(!airline.getAirports().isEmpty()) {
            for (Airport airport : airline.getAirports()) {
                airport.getAirlines().removeIf(a -> a.getCode().equals(code));
            }
        }

        if(!airline.getAirplanes().isEmpty()) {
            for (Airplane airplane : airline.getAirplanes()) {
                airplane.setAirline(null);
            }
        }

        airlineRepository.deleteByCode(code);
    }
}
