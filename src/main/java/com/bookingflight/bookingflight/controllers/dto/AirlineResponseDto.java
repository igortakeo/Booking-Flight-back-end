package com.bookingflight.bookingflight.controllers.dto;

import java.util.List;

public class AirlineResponseDto {

    private String code;

    private String name;

    private Integer numberPlanes;

    private String email;

    private String telephone;

    private List<AirportWithoutListResponseDto>  airports;

    private List<AirplaneWithoutListResponseDto> airplanes;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberPlanes() {
        return numberPlanes;
    }

    public void setNumberPlanes(Integer numberPlanes) {
        this.numberPlanes = numberPlanes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<AirportWithoutListResponseDto> getAirports() {
        return airports;
    }

    public void setAirports(List<AirportWithoutListResponseDto> airports) {
        this.airports = airports;
    }

    public List<AirplaneWithoutListResponseDto> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(List<AirplaneWithoutListResponseDto> airplanes) {
        this.airplanes = airplanes;
    }
}
