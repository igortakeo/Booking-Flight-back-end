package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.domain.Airline;
import com.bookingflight.bookingflight.domain.services.AirlineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/airline")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @GetMapping(value = "/{code}")
    public ResponseEntity<Airline> findByCode(@PathVariable String code){
        Airline airline = airlineService.findByCode(code);

        return ResponseEntity.ok().body(airline);
    }

    @GetMapping
    public ResponseEntity<List<Airline>> findAll(){
        List<Airline> airlines = airlineService.findAll();

        return ResponseEntity.ok().body(airlines);
    }

    @PostMapping
    public ResponseEntity<Airline> create(@RequestBody Airline obj){
        Airline airline  = airlineService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(airline.getCode()).toUri();

        return ResponseEntity.created(endpoint).build();
    }
}
