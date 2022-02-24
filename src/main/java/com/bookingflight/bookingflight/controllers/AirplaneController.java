package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.AirplaneResponseDto;
import com.bookingflight.bookingflight.domain.Airplane;
import com.bookingflight.bookingflight.domain.services.AirplaneService;
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
@RequestMapping(value = "/airplane")
@Api(tags = "Airplane")
public class AirplaneController {

    private final AirplaneService airplaneService;

    private final ModelMapper modelMapper;

    public AirplaneController(AirplaneService airplaneService, ModelMapper modelMapper) {
        this.airplaneService = airplaneService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get an airplane by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AirplaneResponseDto> findById(@PathVariable Long id){
        Airplane airplane = airplaneService.findById(id);

        AirplaneResponseDto airplaneResponseDto = modelMapper.map(airplane, AirplaneResponseDto.class);

        return ResponseEntity.ok().body(airplaneResponseDto);
    }

    @ApiOperation(value = "Get all airplanes")
    @GetMapping
    public ResponseEntity<List<AirplaneResponseDto>> findAll(){
        List<Airplane> airplaneList = airplaneService.findAll();

        List<AirplaneResponseDto> airplaneResponseDtoList = new ArrayList<>();

        for(Airplane airplane : airplaneList){
            airplaneResponseDtoList.add(modelMapper.map(airplane, AirplaneResponseDto.class));
        }

        return ResponseEntity.ok().body(airplaneResponseDtoList);
    }

    @ApiOperation(value = "Create new airplane")
    @PostMapping
    public ResponseEntity<Airplane> create(@RequestBody Airplane obj){
        Airplane airplane  = airplaneService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(airplane.getId()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @ApiOperation(value = "Update an airplane")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AirplaneResponseDto> update(@PathVariable Long id,@RequestBody Airplane obj){
        Airplane airplane = airplaneService.update(id, obj);

        AirplaneResponseDto airplaneResponseDto = modelMapper.map(airplane, AirplaneResponseDto.class);

        return ResponseEntity.ok().body(airplaneResponseDto);
    }

    @ApiOperation(value = "Delete an airplane")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        airplaneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}