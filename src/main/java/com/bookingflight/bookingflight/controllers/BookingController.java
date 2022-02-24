package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.BookingResponseDto;
import com.bookingflight.bookingflight.domain.Booking;
import com.bookingflight.bookingflight.domain.services.BookingService;
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
@RequestMapping(value = "/booking")
@Api(tags = "Booking")
public class BookingController {

    private final BookingService bookingService;

    private final ModelMapper modelMapper;

    public BookingController(BookingService bookingService, ModelMapper modelMapper) {
        this.bookingService = bookingService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get a booking by CPF")
    @GetMapping(value = "/passenger/{cpf}")
    public ResponseEntity<List<BookingResponseDto>> findByCpf(@PathVariable String cpf){
        List<Booking> bookings = bookingService.findByCpf(cpf);

        List<BookingResponseDto> bookingResponseDtoList = new ArrayList<>();

        for(Booking booking : bookings){
            bookingResponseDtoList.add(modelMapper.map(booking, BookingResponseDto.class));
        }

        return ResponseEntity.ok().body(bookingResponseDtoList);
    }

    @ApiOperation(value = "Get a booking by Flight id")
    @GetMapping(value = "/flight/{id}")
    public ResponseEntity<List<BookingResponseDto>> findById(@PathVariable Long id){
        List<Booking> bookings = bookingService.findById(id);

        List<BookingResponseDto> bookingResponseDtoList = new ArrayList<>();

        for(Booking booking : bookings){
            bookingResponseDtoList.add(modelMapper.map(booking, BookingResponseDto.class));
        }

        return ResponseEntity.ok().body(bookingResponseDtoList);
    }

    @ApiOperation(value = "Get all bookings")
    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> listAll(){
        List<Booking> bookingList = bookingService.findAll();

        List<BookingResponseDto> bookingResponseDtoList = new ArrayList<>();

        for (Booking booking : bookingList){
            bookingResponseDtoList.add(modelMapper.map(booking, BookingResponseDto.class));
        }

        return ResponseEntity.ok().body(bookingResponseDtoList);
    }

    @ApiOperation(value = "Create new booking")
    @PostMapping
    public ResponseEntity<BookingResponseDto> create(@RequestBody Booking obj){
        Booking booking = bookingService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/passenger/{cpf}")
                .buildAndExpand(booking.getPassenger().getCpf()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @ApiOperation(value = "Update a booking")
    @PutMapping(value = "/{cpf}/{id}")
    public ResponseEntity<BookingResponseDto> update(
            @PathVariable String cpf,
            @PathVariable Long id,
            @RequestBody Booking obj){

        BookingResponseDto bookingResponseDto = null;

        return ResponseEntity.ok().body(bookingResponseDto);
    }

    @ApiOperation(value = "Delete a booking")
    @DeleteMapping(value = "/{cpf}/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String cpf, @PathVariable Long id){
        bookingService.delete(cpf, id);
        return ResponseEntity.noContent().build();
    }
}
