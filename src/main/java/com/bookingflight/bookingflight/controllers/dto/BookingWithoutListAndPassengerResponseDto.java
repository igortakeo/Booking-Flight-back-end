package com.bookingflight.bookingflight.controllers.dto;

import com.bookingflight.bookingflight.domain.ClassTravelEnum;

public class BookingWithoutListAndPassengerResponseDto {

    private FlightWithoutListResponseDto flight;

    private ClassTravelEnum classTravel;

    private Integer seat;

    private ClassFlightWithoutListResponseDto classFlight;

    public ClassTravelEnum getClassTravel() {
        return classTravel;
    }

    public void setClassTravel(ClassTravelEnum classTravel) {
        this.classTravel = classTravel;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public ClassFlightWithoutListResponseDto getClassFlight() {
        return classFlight;
    }

    public void setClassFlight(ClassFlightWithoutListResponseDto classFlight) {
        this.classFlight = classFlight;
    }

    public FlightWithoutListResponseDto getFlight() {
        return flight;
    }

    public void setFlight(FlightWithoutListResponseDto flight) {
        this.flight = flight;
    }
}
