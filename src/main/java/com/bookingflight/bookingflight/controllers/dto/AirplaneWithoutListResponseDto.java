package com.bookingflight.bookingflight.controllers.dto;

public class AirplaneWithoutListResponseDto {

    private Long id;

    private String name;

    private Integer maximumNumberPassengers;

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
}
