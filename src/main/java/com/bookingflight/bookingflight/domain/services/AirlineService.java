package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Airline;
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

        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found with Code = " + code));
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
}
