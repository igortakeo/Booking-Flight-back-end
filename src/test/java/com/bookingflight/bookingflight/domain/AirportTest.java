package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.GeneratedValue;

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
}