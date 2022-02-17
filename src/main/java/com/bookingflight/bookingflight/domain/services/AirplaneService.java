package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Airplane;
import com.bookingflight.bookingflight.domain.Flight;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.AirplaneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;

    public AirplaneService(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    public Airplane findById(Long id) {
        Optional<Airplane> obj = airplaneRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found with Id = " + id));
    }

    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }

    public Airplane create(Airplane obj) {
        Optional<Airplane> airplane = airplaneRepository.findById(obj.getId());

        if(airplane.isPresent()){
            throw new ObjectAlreadyExistException("Object already exist");
        }

        return airplaneRepository.save(obj);
    }

    public Airplane update(Long id, Airplane obj) {
        Airplane airplane = findById(id);

        Airplane airplaneVerify = airplaneRepository.findByName(obj.getName());

        if(airplaneVerify != null && !airplane.getId().equals(airplaneVerify.getId())){
            throw new ObjectAlreadyExistException("Object already exist (change name)");
        }

        airplane.setName(obj.getName());
        airplane.setMaximumNumberPassengers(obj.getMaximumNumberPassengers());

        return airplaneRepository.save(airplane);
    }

    public void delete(Long id) {
        Airplane airplane = findById(id);

        if(!airplane.getFlights().isEmpty()){
            for(Flight flight : airplane.getFlights()){
                flight.setAirplane(null);
            }
        }


        // delete airline


        airplaneRepository.deleteById(airplane.getId());
    }
}
