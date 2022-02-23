package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.ClassFlight;
import com.bookingflight.bookingflight.domain.ClassTravelEnum;
import com.bookingflight.bookingflight.domain.Flight;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectWithIncorretInformationsException;
import com.bookingflight.bookingflight.repositories.ClassFlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClassFlightService {

    private final ClassFlightRepository classFlightRepository;

    private final FlightService flightService;

    public ClassFlightService(ClassFlightRepository classFlightRepository, FlightService flightService) {
        this.classFlightRepository = classFlightRepository;
        this.flightService = flightService;
    }

    public ClassFlight findByClassTravelAndFlightId(ClassTravelEnum classTravel, Long id) {
        Optional<ClassFlight> obj = classFlightRepository.findByClassTravelAndFlightId(classTravel, id);

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found with Class Travel = " + classTravel + " and Flight Id = " + id));
    }

    public List<ClassFlight> findAll() {
        return classFlightRepository.findAll();
    }

    public List<ClassFlight> findById(Long id) {
        Optional<List<ClassFlight>> obj = classFlightRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found with Flight Id = " + id));
    }

    public ClassFlight create(ClassFlight obj) {

        Flight flight = flightService.findById(obj.getFlight().getId());
        obj.setFlight(flight);

        Optional<ClassFlight> classFlightVerify = classFlightRepository.findByClassTravelAndFlightId(
                obj.getClassTravel(),
                obj.getFlight().getId()
        );

        if(classFlightVerify.isPresent()){
            throw new ObjectAlreadyExistException("Class FLight already exist");
        }

        return classFlightRepository.save(obj);
    }

    public ClassFlight update(Long id, ClassTravelEnum classTravel, ClassFlight classFlightUpdate) {
        ClassFlight classFlight = findByClassTravelAndFlightId(classTravel, id);

        if(!Objects.equals(classFlightUpdate.getFlight().getId(), id) ||
            classFlightUpdate.getClassTravel() != classTravel){
            throw  new ObjectWithIncorretInformationsException("Id or Class travel incorrect");
        }

        classFlight.setPrice(classFlightUpdate.getPrice());

        return classFlightRepository.save(classFlight);
    }

    public void delete(Long id, ClassTravelEnum classTravel) {
        ClassFlight classFlight = findByClassTravelAndFlightId(classTravel, id);
        Flight flight = flightService.findById(id);

        flight.getClassFlights().removeIf(
                cf -> cf.getFlight().getId().equals(id) && cf.getClassTravel().equals(classTravel));

        classFlightRepository.delete(classFlight);
    }
}
