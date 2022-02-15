package com.bookingflight.bookingflight.controllers.dto;

import java.util.List;

public class AirportResponseDto {

    private Long id;

    private String name;

    private String street;

    private Integer number;

    private String cep;

    private String city;

    private List<AirlineAirportResponseDto> airlines;

    private List<FlightAirportResponseDto> flights;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<AirlineAirportResponseDto> getAirlines() {
        return airlines;
    }

    public void setAirlines(List<AirlineAirportResponseDto> airlines) {
        this.airlines = airlines;
    }

    public List<FlightAirportResponseDto> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightAirportResponseDto> flights) {
        this.flights = flights;
    }
}