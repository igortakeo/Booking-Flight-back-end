package com.bookingflight.bookingflight.controllers.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FlightResponseDto {

    private Long id;

    private AirportWithoutListResponseDto airport;

    private AirplaneWithoutListResponseDto airplane;

    private String source;

    private String target;

    private LocalDateTime date;

    private List<BookingWithoutListResponseDto> bookings;

    private List<ClassFlightWithoutListResponseDto> classFlights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AirportWithoutListResponseDto getAirport() {
        return airport;
    }

    public void setAirport(AirportWithoutListResponseDto airport) {
        this.airport = airport;
    }

    public AirplaneWithoutListResponseDto getAirplane() {
        return airplane;
    }

    public void setAirplane(AirplaneWithoutListResponseDto airplane) {
        this.airplane = airplane;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<BookingWithoutListResponseDto> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingWithoutListResponseDto> bookings) {
        this.bookings = bookings;
    }

    public List<ClassFlightWithoutListResponseDto> getClassFlights() {
        return classFlights;
    }

    public void setClassFlights(List<ClassFlightWithoutListResponseDto> classFlights) {
        this.classFlights = classFlights;
    }
}