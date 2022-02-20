package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.FlightResponseDto;
import com.bookingflight.bookingflight.domain.Flight;
import com.bookingflight.bookingflight.domain.services.FlightService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/flight")
public class FlightController {

    private final FlightService flightService;

    private final ModelMapper modelMapper;

    public FlightController(FlightService flightService, ModelMapper modelMapper) {
        this.flightService = flightService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FlightResponseDto> findById(@PathVariable Long id){
        Flight flight = flightService.findById(id);

        FlightResponseDto flightResponseDto = modelMapper.map(flight, FlightResponseDto.class);

        return ResponseEntity.ok().body(flightResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<FlightResponseDto>> findAll(){
        List<Flight> flightList = flightService.findAll();

        List<FlightResponseDto> flightResponseDtoList = new ArrayList<>();

        for (Flight flight : flightList){
            flightResponseDtoList.add(modelMapper.map(flight, FlightResponseDto.class));
        }

        return ResponseEntity.ok().body(flightResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<FlightResponseDto> create(@RequestBody Flight obj){
        Flight flight = flightService.create(obj);

        FlightResponseDto flightResponseDto = modelMapper.map(flight, FlightResponseDto.class);

        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(flightResponseDto.getId()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FlightResponseDto> update(@PathVariable Long id, @RequestBody Flight obj){
        Flight flight = flightService.update(id, obj);

        FlightResponseDto flightResponseDto = modelMapper.map(flight, FlightResponseDto.class);

        return ResponseEntity.ok().body(flightResponseDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        flightService.delete(id);
        return ResponseEntity.noContent().build();
    }
}