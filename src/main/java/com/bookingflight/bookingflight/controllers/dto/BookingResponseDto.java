package com.bookingflight.bookingflight.controllers.dto;

public class BookingResponseDto {

    private PassengerWithoutListResponseDto passenger;

    private FlightWithoutListResponseDto flight;

    private Integer seat;

    private ClassFlightWithoutListResponseDto classFlight;

    public PassengerWithoutListResponseDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerWithoutListResponseDto passenger) {
        this.passenger = passenger;
    }

    public FlightWithoutListResponseDto getFlight() {
        return flight;
    }

    public void setFlight(FlightWithoutListResponseDto flight) {
        this.flight = flight;
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
}
