package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.AirplaneResponseDto;
import com.bookingflight.bookingflight.domain.Airplane;
import com.bookingflight.bookingflight.domain.services.AirplaneService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/airplane")
public class AirplaneController {

    private final AirplaneService airplaneService;

    private final ModelMapper modelMapper;

    public AirplaneController(AirplaneService airplaneService, ModelMapper modelMapper) {
        this.airplaneService = airplaneService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AirplaneResponseDto> findById(@PathVariable Long id){
        Airplane airplane = airplaneService.findById(id);

        AirplaneResponseDto airplaneResponseDto = modelMapper.map(airplane, AirplaneResponseDto.class);

        return ResponseEntity.ok().body(airplaneResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<AirplaneResponseDto>> findAll(){
        List<Airplane> airplaneList = airplaneService.findAll();

        List<AirplaneResponseDto> airplaneResponseDtoList = new ArrayList<>();

        for(Airplane airplane : airplaneList){
            airplaneResponseDtoList.add(modelMapper.map(airplane, AirplaneResponseDto.class));
        }

        return ResponseEntity.ok().body(airplaneResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<Airplane> create(@RequestBody Airplane obj){
        Airplane airplane  = airplaneService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(airplane.getId()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AirplaneResponseDto> update(@PathVariable Long id,@RequestBody Airplane obj){
        Airplane airplane = airplaneService.update(id, obj);

        AirplaneResponseDto airplaneResponseDto = modelMapper.map(airplane, AirplaneResponseDto.class);

        return ResponseEntity.ok().body(airplaneResponseDto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        airplaneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}