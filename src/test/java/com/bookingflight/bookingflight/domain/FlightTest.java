package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {

    private Flight flight;

    @BeforeEach
    public void setUp(){
        this.flight = new Flight();
    }

    @Test
    void getId() {
        final Long id = 1L;

        flight.setId(id);

        assertEquals(id, flight.getId());
    }

    @Test
    void getAirport() {
        final Airport airport = new Airport(
                null,
                "airportTest",
                "streetTest",
                10,
                "cepTest",
                "cityTest"
        );

        flight.setAirport(airport);

        assertEquals(airport, flight.getAirport());
    }

    @Test
    void getAirplane() {
        final Airplane airplane = new Airplane(
                null,
                "airplaneTest",
                50
        );

        flight.setAirplane(airplane);

        assertEquals(airplane, flight.getAirplane());
    }

    @Test
    void getSource() {
        final String source = "sourceTest";

        flight.setSource(source);

        assertEquals(source, flight.getSource());
    }

    @Test
    void getTarget() {
        final String target = "targetTest";

        flight.setTarget(target);

        assertEquals(target, flight.getTarget());
    }

    @Test
    void getDate() {
        final LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        flight.setDate(date);

        assertEquals(date, flight.getDate());
    }
}