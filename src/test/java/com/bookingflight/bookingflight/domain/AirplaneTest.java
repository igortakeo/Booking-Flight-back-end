package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}