package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.AirlineWithoutListResponseDto;
import com.bookingflight.bookingflight.controllers.dto.AirportResponseDto;
import com.bookingflight.bookingflight.domain.Airline;
import com.bookingflight.bookingflight.domain.Airport;
import com.bookingflight.bookingflight.domain.services.AirportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/airport")
@Api(tags = "Airport")
public class AirportController {

    private final AirportService airportService;

    private final ModelMapper modelMapper;

    public AirportController(AirportService airportService, ModelMapper modelMapper) {
        this.airportService = airportService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get an airport by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AirportResponseDto> findById(@PathVariable Long id){
        Airport airport = airportService.findById(id);

        AirportResponseDto airportResponseDto = modelMapper.map(airport, AirportResponseDto.class);

        return ResponseEntity.ok().body(airportResponseDto);
    }

    @ApiOperation(value = "Get all airports")
    @GetMapping
    public ResponseEntity<List<AirportResponseDto>> findAll(){
        List<Airport> airports = airportService.findAll();

        List<AirportResponseDto> airportResponseDtoList = new ArrayList<>();

        for(Airport airport : airports){
            airportResponseDtoList.add(modelMapper.map(airport, AirportResponseDto.class));
        }

        return ResponseEntity.ok().body(airportResponseDtoList);
    }

    @ApiOperation(value = "Get all airlines that belongs to an airport")
    @GetMapping(value = "/{id}/airlines")
    public ResponseEntity<List<AirlineWithoutListResponseDto>> findAirlines(@PathVariable Long id){
        List<Airline> airlines = airportService.findAirlines(id);

        List<AirlineWithoutListResponseDto> airlineAirportResponseDtoList = new ArrayList<>();

        for(Airline airline : airlines){
            airlineAirportResponseDtoList.add((modelMapper.map(airline, AirlineWithoutListResponseDto.class)));
        }

        return ResponseEntity.ok().body(airlineAirportResponseDtoList);
    }

    @ApiOperation(value = "Create new airport")
    @PostMapping
    public ResponseEntity<Airport> create(@RequestBody Airport obj){
        Airport newAirport = airportService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newAirport.getId()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @ApiOperation(value = "Add airline at an airport")
    @PostMapping(value = "/{id}/airlines/{code}")
    public ResponseEntity<Airport> addAirline(@PathVariable Long id, @PathVariable String code){
        Airport airport = airportService.addAirline(id, code);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/airlines").buildAndExpand(airport.getId()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @ApiOperation(value = "Update an airport")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AirportResponseDto> update(@PathVariable Long id, @RequestBody Airport obj){
        Airport newAirport = airportService.update(id, obj);

        AirportResponseDto airportResponseDto = modelMapper.map(newAirport, AirportResponseDto.class);

        return ResponseEntity.ok().body(airportResponseDto);
    }

    @ApiOperation(value = "Delete an airport")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
