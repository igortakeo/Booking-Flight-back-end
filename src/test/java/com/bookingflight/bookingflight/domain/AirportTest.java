package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.GeneratedValue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AirportTest {

    private Airport airport;

    @BeforeEach
    public void setUp(){
        this.airport = new Airport();
    }

    @Test
    void getId() {
        final Long id = 1L;

        airport.setId(id);

        assertEquals(id, airport.getId());
    }

    @Test
    void getName() {
        final String name = "nameTest";

        airport.setName(name);

        assertEquals(name, airport.getName());
    }

    @Test
    void getStreet() {
        final String street = "streetTest";

        airport.setStreet(street);

        assertEquals(street, airport.getStreet());
    }

    @Test
    void getNumber() {
        final Integer number = 10;

        airport.setNumber(number);

        assertEquals(number, airport.getNumber());
    }

    @Test
    void getCep() {
        final String cep = "cepTest";

        airport.setCep(cep);

        assertEquals(cep, airport.getCep());
    }

    @Test
    void getCity() {
        final String city = "cityTest";

        airport.setCity(city);

        assertEquals(city, airport.getCity());
    }

    @Test
    void getAirlines() {
        final Airline airlineTest1 = new Airline(
                "codeTest1",
                "airlline1",
                30,
                "emailTest1",
                "telephoneTest1");

        final Airline airlineTest2 = new Airline(
                "codeTest2",
                "airlline2",
                50,
                "emailTest2",
                "telephoneTest2");

        final List<Airline> airlineList = new ArrayList<>(Arrays.asList(airlineTest1, airlineTest2));

        airport.setAirlines(airlineList);

        List<Airline> airlineListReturn = airport.getAirlines();

        for(int i=0; i<airlineListReturn.size(); i++){
            Airline airline = airlineListReturn.get(i);
            assertEquals(airline, airlineListReturn.get(i));
        }
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

        final List<Flight>flightList = new ArrayList<>(Arrays.asList(flight1, flight2));

        airport.setFlights(flightList);

        List<Flight>flightListReturn = airport.getFlights();

        for(int i=0; i<flightListReturn.size(); i++){
            Flight flight = flightListReturn.get(i);
            assertEquals(flight, flightListReturn.get(i));
        }
    }
}