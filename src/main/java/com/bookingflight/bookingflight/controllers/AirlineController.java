package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.AirlineResponseDto;
import com.bookingflight.bookingflight.domain.Airline;
import com.bookingflight.bookingflight.domain.services.AirlineService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/airline")
public class AirlineController {

    private final AirlineService airlineService;

    private final ModelMapper modelMapper;

    public AirlineController(AirlineService airlineService, ModelMapper modelMapper) {
        this.airlineService = airlineService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{code}")
    public ResponseEntity<AirlineResponseDto> findByCode(@PathVariable String code){
        Airline airline = airlineService.findByCode(code);

        AirlineResponseDto airlineResponseDto = modelMapper.map(airline, AirlineResponseDto.class);

        return ResponseEntity.ok().body(airlineResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<AirlineResponseDto>> findAll(){
        List<Airline> airlines = airlineService.findAll();

        List<AirlineResponseDto> airlineResponseDtoList = new ArrayList<>();

        for(Airline airline : airlines){
            airlineResponseDtoList.add(modelMapper.map(airline, AirlineResponseDto.class));
        }

        return ResponseEntity.ok().body(airlineResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<Airline> create(@RequestBody Airline obj){
        Airline airline  = airlineService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(airline.getCode()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @PutMapping(value = "/{code}")
    public ResponseEntity<AirlineResponseDto> update(@PathVariable String code, @RequestBody Airline obj){
        Airline newAirline = airlineService.update(code, obj);

        AirlineResponseDto airlineResponseDto = modelMapper.map(newAirline, AirlineResponseDto.class);

        return ResponseEntity.ok().body(airlineResponseDto);
    }

    @DeleteMapping(value = "/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String code){
        airlineService.delete(code);
        return ResponseEntity.noContent().build();
    }

}
