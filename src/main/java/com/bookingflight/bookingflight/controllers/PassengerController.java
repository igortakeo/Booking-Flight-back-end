package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.PassengerResponseDto;
import com.bookingflight.bookingflight.domain.Passenger;
import com.bookingflight.bookingflight.domain.services.PassengerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/passenger")
@Api(tags = "Passenger")
public class PassengerController {

    private final PassengerService passengerService;

    private final ModelMapper modelMapper;

    public PassengerController(PassengerService passengerService, ModelMapper modelMapper) {
        this.passengerService = passengerService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get a passenger by CPF")
    @GetMapping(value = "/{cpf}")
    public ResponseEntity<PassengerResponseDto> findByCpf(@PathVariable String cpf){
        Passenger passenger = passengerService.findByCpf(cpf);

        PassengerResponseDto passengerResponseDto = modelMapper.map(passenger, PassengerResponseDto.class);

        return ResponseEntity.ok().body(passengerResponseDto);
    }

    @ApiOperation(value = "Get all passengers")
    @GetMapping
    public ResponseEntity<Page<PassengerResponseDto>> findAll(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy
    ){
        Page<Passenger> passengerPage = passengerService.findAll(page, sortBy);

        List<Passenger> passengerList = passengerPage.getContent();
        List<PassengerResponseDto> passengerResponseDtos = new ArrayList<>();

        for(Passenger passenger : passengerList){
            passengerResponseDtos.add(modelMapper.map(passenger, PassengerResponseDto.class));
        }

        Page<PassengerResponseDto> passengerResponseDtoPage = new PageImpl<>(passengerResponseDtos);

        return ResponseEntity.ok().body(passengerResponseDtoPage);
    }

    @ApiOperation(value = "Create new passenger")
    @PostMapping
    public ResponseEntity<Passenger> create(@RequestBody Passenger obj){
        Passenger passenger = passengerService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf}").buildAndExpand(passenger.getCpf()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @ApiOperation(value = "Update a passenger")
    @PutMapping(value = "/{cpf}")
    public ResponseEntity<PassengerResponseDto> update(@PathVariable String cpf, @RequestBody Passenger obj){
        Passenger passenger = passengerService.update(cpf, obj);

        PassengerResponseDto passengerResponseDto = modelMapper.map(passenger, PassengerResponseDto.class);

        return ResponseEntity.ok().body(passengerResponseDto);
    }

    @ApiOperation(value = "Delete a passenger")
    @DeleteMapping(value = "/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String cpf){
        passengerService.delete(cpf);
        return ResponseEntity.noContent().build();
    }
}
