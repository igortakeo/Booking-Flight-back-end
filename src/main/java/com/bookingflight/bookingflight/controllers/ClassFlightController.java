package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.ClassFlightResponseDto;
import com.bookingflight.bookingflight.domain.ClassFlight;
import com.bookingflight.bookingflight.domain.ClassTravelEnum;
import com.bookingflight.bookingflight.domain.services.ClassFlightService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/classflight")
@Api(tags = "Class Flight")
public class ClassFlightController {

    private final ClassFlightService classFlightService;

    private final ModelMapper modelMapper;

    public ClassFlightController(ClassFlightService classFlightService, ModelMapper modelMapper) {
        this.classFlightService = classFlightService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get class flight by class and flight id")
    @GetMapping(value = "/{id}/{classTravel}")
    public ResponseEntity<ClassFlightResponseDto> findByClassTravelAndFlightId(
            @PathVariable Long id,
            @PathVariable ClassTravelEnum classTravel) {

        ClassFlight classFlight = classFlightService.findByClassTravelAndFlightId(classTravel, id);

        ClassFlightResponseDto classFlightResponseDto = modelMapper.map(classFlight, ClassFlightResponseDto.class);

        return ResponseEntity.ok().body(classFlightResponseDto);
    }

    @ApiOperation(value = "Get all class flight belongs to a flight")
    @GetMapping(value = "/{id}")
    public ResponseEntity<List<ClassFlightResponseDto>> findById(@PathVariable Long id){
        List<ClassFlight> classFlightList = classFlightService.findById(id);

        List<ClassFlightResponseDto> classFlightResponseDtoList = new ArrayList<>();

        for(ClassFlight classFlight : classFlightList){
            classFlightResponseDtoList.add(modelMapper.map(classFlight, ClassFlightResponseDto.class));
        }

        return ResponseEntity.ok().body(classFlightResponseDtoList);
    }

    @ApiOperation(value = "Get all class flight")
    @GetMapping
    public ResponseEntity<List<ClassFlightResponseDto>> findAll(){
        List<ClassFlight> classFlightList = classFlightService.findAll();

        List<ClassFlightResponseDto> classFlightResponseDtoList = new ArrayList<>();

        for(ClassFlight classFlight : classFlightList){
            classFlightResponseDtoList.add(modelMapper.map(classFlight, ClassFlightResponseDto.class));
        }

        return ResponseEntity.ok().body(classFlightResponseDtoList);
    }

    @ApiOperation(value = "List all class travel available")
    @GetMapping(value = "/class-travel")
    public ResponseEntity<List<ClassTravelEnum>> listClassTravel(){
        List<ClassTravelEnum> classTravelEnumList = new ArrayList<>(Arrays.asList(
                ClassTravelEnum.FIRST_CLASS,
                ClassTravelEnum.BUSINESS,
                ClassTravelEnum.ECONOMY,
                ClassTravelEnum.PREMIUM_ECONOMY
        ));

        return ResponseEntity.ok().body(classTravelEnumList);
    }

    @ApiOperation(value = "Create new class flight")
    @PostMapping
    public ResponseEntity<ClassFlightResponseDto> create(@RequestBody ClassFlight obj){
        ClassFlight classFlight = classFlightService.create(obj);

        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/{classTravel}").buildAndExpand(
                classFlight.getFlight().getId(),
                classFlight.getClassTravel()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @ApiOperation(value = "Update a class flight")
    @PutMapping(value = "/{id}/{classTravel}")
    public ResponseEntity<ClassFlightResponseDto> update(
            @PathVariable Long id,
            @PathVariable ClassTravelEnum classTravel,
            @RequestBody ClassFlight classFlightUpdate
    ){
        ClassFlight classFlight = classFlightService.update(id, classTravel, classFlightUpdate);

        ClassFlightResponseDto classFlightResponseDto = modelMapper.map(classFlight, ClassFlightResponseDto.class);

        return ResponseEntity.ok().body(classFlightResponseDto);
    }

    @ApiOperation(value = "Delete a class flight")
    @DeleteMapping(value = "/{id}/{classTravel}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable ClassTravelEnum classTravel){
        classFlightService.delete(id, classTravel);
        return ResponseEntity.noContent().build();
    }
}