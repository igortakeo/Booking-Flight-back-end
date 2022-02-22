package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.BookingResponseDto;
import com.bookingflight.bookingflight.domain.Booking;
import com.bookingflight.bookingflight.domain.services.BookingService;
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
public class BookingController {

    private final BookingService bookingService;

    private final ModelMapper modelMapper;

    public BookingController(BookingService bookingService, ModelMapper modelMapper) {
        this.bookingService = bookingService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/passenger/{cpf}")
    public ResponseEntity<List<BookingResponseDto>> findByCpf(@PathVariable String cpf){
        List<Booking> bookings = bookingService.findByCpf(cpf);

        List<BookingResponseDto> bookingResponseDtoList = new ArrayList<>();

        for(Booking booking : bookings){
            bookingResponseDtoList.add(modelMapper.map(booking, BookingResponseDto.class));
        }

        return ResponseEntity.ok().body(bookingResponseDtoList);
    }

    @GetMapping(value = "/flight/{id}")
    public ResponseEntity<List<BookingResponseDto>> findById(@PathVariable Long id){
        List<Booking> bookings = bookingService.findById(id);

        List<BookingResponseDto> bookingResponseDtoList = new ArrayList<>();

        for(Booking booking : bookings){
            bookingResponseDtoList.add(modelMapper.map(booking, BookingResponseDto.class));
        }

        return ResponseEntity.ok().body(bookingResponseDtoList);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> listAll(){
        List<Booking> bookingList = bookingService.findAll();

        List<BookingResponseDto> bookingResponseDtoList = new ArrayList<>();

        for (Booking booking : bookingList){
            bookingResponseDtoList.add(modelMapper.map(booking, BookingResponseDto.class));
        }

        return ResponseEntity.ok().body(bookingResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> create(@RequestBody Booking obj){
        Booking booking = bookingService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/passenger/{cpf}")
                .buildAndExpand(booking.getPassenger().getCpf()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @PutMapping(value = "/{cpf}/{id}")
    public ResponseEntity<BookingResponseDto> update(
            @PathVariable String cpf,
            @PathVariable Long id,
            @RequestBody Booking obj){

        BookingResponseDto bookingResponseDto = null;

        return ResponseEntity.ok().body(bookingResponseDto);
    }

    @DeleteMapping(value = "/{cpf}/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String cpf, @PathVariable Long id){
        bookingService.delete(cpf, id);
        return ResponseEntity.noContent().build();
    }
}
