package com.bookingflight.bookingflight.controllers.dto;

import java.util.List;

public class AirplaneResponseDto {

    private Long id;

    private String name;

    private Integer maximumNumberPassengers;

    private AirlineWithoutListResponseDto airline;

    private List<FlightWithoutListResponseDto> flights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaximumNumberPassengers() {
        return maximumNumberPassengers;
    }

    public void setMaximumNumberPassengers(Integer maximumNumberPassengers) {
        this.maximumNumberPassengers = maximumNumberPassengers;
    }

    public AirlineWithoutListResponseDto getAirline() {
        return airline;
    }

    public void setAirline(AirlineWithoutListResponseDto airline) {
        this.airline = airline;
    }

    public List<FlightWithoutListResponseDto> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightWithoutListResponseDto> flights) {
        this.flights = flights;
    }
}