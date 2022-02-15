package com.bookingflight.bookingflight.controllers.dto;

public class AirlineAirportResponseDto {

    private String code;

    private String name;

    private Integer numberPlanes;

    private String email;

    private String telephone;

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
}