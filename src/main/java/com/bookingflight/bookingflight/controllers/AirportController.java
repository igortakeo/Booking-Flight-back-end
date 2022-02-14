package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.domain.Airport;
import com.bookingflight.bookingflight.domain.services.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/airport")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Airport> findById(@PathVariable Long id){
        Airport airport = airportService.findById(id);

        return ResponseEntity.ok().body(airport);
    }

    @GetMapping
    public ResponseEntity<List<Airport>> findAll(){
        List<Airport> airports = airportService.findAll();

        return ResponseEntity.ok().body(airports);
    }

    @PostMapping
    public ResponseEntity<Airport> create(@RequestBody Airport obj){
        Airport newAirport = airportService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newAirport.getId()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

}
