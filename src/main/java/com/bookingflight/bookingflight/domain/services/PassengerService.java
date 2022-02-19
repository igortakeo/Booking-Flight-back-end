package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Passenger;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger findByCpf(String cpf) {
        Optional<Passenger> obj = passengerRepository.findByCpf(cpf);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found with Id = " + cpf));
    }

    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    public Passenger create(Passenger obj) {
        return passengerRepository.save(obj);
    }

    public Passenger update(String cpf, Passenger obj) {
        Passenger passenger = findByCpf(cpf);

        passenger.setName(obj.getName());
        passenger.setLastName(obj.getLastName());
        passenger.setAge(obj.getAge());
        passenger.setEmail(obj.getEmail());
        passenger.setTelephone(obj.getTelephone());

        return passengerRepository.save(passenger);
    }

    public void delete(String cpf) {
        Passenger passenger = findByCpf(cpf);
        passengerRepository.delete(passenger);
    }
}
