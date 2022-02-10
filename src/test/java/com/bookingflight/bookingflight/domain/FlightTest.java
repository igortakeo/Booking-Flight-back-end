package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void getBookings() {
        this.flight.setSource("Sao Paulo");
        this.flight.setTarget("Rio de Janeiro");
        this.flight.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        final Passenger passenger = new Passenger(
                "95466724084",
                "nameTest",
                "lastNameTest",
                22,
                "emailTest",
                "telephoneTest"
        );

        final Booking booking1 = new Booking(
                passenger,
                this.flight,
                ClassTravelEnum.FIRST_CLASS
        );

        final Booking booking2 = new Booking(
                passenger,
                this.flight,
                ClassTravelEnum.BUSINESS
        );

        final List<Booking> bookingList = new ArrayList<>(Arrays.asList(booking1, booking2));

        flight.setBookings(bookingList);

        List<Booking> bookingListReturn = flight.getBookings();

        for(int i=0; i<bookingListReturn.size(); i++){
            Booking booking = bookingListReturn.get(i);
            assertEquals(bookingList.get(i), booking);
        }
    }
}