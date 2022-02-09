package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AirplaneTest {

    private Airplane airplane;

    @BeforeEach
    public void setUp(){
        this.airplane = new Airplane();
    }

    @Test
    void getId() {
        final Long id = 1L;

        airplane.setId(id);

        assertEquals(id, airplane.getId());
    }

    @Test
    void getName() {
        final String name = "nameTest";

        airplane.setName(name);

        assertEquals(name, airplane.getName());
    }

    @Test
    void getMaximumNumberPassengers() {
        final Integer maximumNumberPassengers = 50;

        airplane.setMaximumNumberPassengers(maximumNumberPassengers);

        assertEquals(maximumNumberPassengers, airplane.getMaximumNumberPassengers());
    }

    @Test
    void getAirline() {
        final Airline airline = new Airline(
                "codeTest",
                "nameTest",
                20,
                "emailTest",
                "telephoneTest");

        airplane.setAirline(airline);

        assertEquals(airline, airplane.getAirline());
    }

    @Test
    void getFlights() {
        final Flight flight1 = new Flight(
                null,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flight2 = new Flight(
                null,
                "Sao Paulo",
                "Belo Horizonte",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final List<Flight> flightList = new ArrayList<>(Arrays.asList(flight1, flight2));

        airplane.setFlights(flightList);

        List<Flight>flightListReturn = airplane.getFlights();

        for(int i=0; i<flightListReturn.size(); i++){
            Flight flight = flightListReturn.get(i);
            assertEquals(flight, flightListReturn.get(i));
        }
    }
}