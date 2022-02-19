package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.domain.Passenger;
import com.bookingflight.bookingflight.domain.services.PassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/passenger")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping(value = "/{cpf}")
    public ResponseEntity<Passenger> findByCpf(@PathVariable String cpf){
        Passenger passenger = passengerService.findByCpf(cpf);

        return ResponseEntity.ok().body(passenger);
    }

    @GetMapping
    public ResponseEntity<List<Passenger>> findAll(){
        List<Passenger> passengerList = passengerService.findAll();

        return ResponseEntity.ok().body(passengerList);
    }

    @PostMapping
    public ResponseEntity<Passenger> create(@RequestBody Passenger obj){
        Passenger passenger = passengerService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf}").buildAndExpand(passenger.getCpf()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @PutMapping(value = "/{cpf}")
    public ResponseEntity<Passenger> update(@PathVariable String cpf, @RequestBody Passenger obj){
        Passenger passenger = passengerService.update(cpf, obj);

        return ResponseEntity.ok().body(passenger);
    }

    @DeleteMapping(value = "/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String cpf){
        passengerService.delete(cpf);
        return ResponseEntity.noContent().build();
    }
}