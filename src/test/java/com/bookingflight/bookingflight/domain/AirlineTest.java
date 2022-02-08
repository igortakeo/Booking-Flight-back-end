package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirlineTest {

    private Airline airline;

    @BeforeEach
    public void setUp(){
        this.airline = new Airline();
    }

    @Test
    void getCode() {
        final String code = "codeTest";

        airline.setCode(code);

        assertEquals(code, airline.getCode());
    }

    @Test
    void getName() {
        final String name = "nameTest";

        airline.setName(name);

        assertEquals(name, airline.getName());
    }

    @Test
    void getNumberPlanes() {
        final Integer numberPlanes = 10;

        airline.setNumberPlanes(numberPlanes);

        assertEquals(numberPlanes, airline.getNumberPlanes());
    }

    @Test
    void getEmail() {
        final String email = "email_teste@gmail.com";

        airline.setEmail(email);

        assertEquals(email, airline.getEmail());
    }

    @Test
    void getTelephone() {
        final String telephone = "4399795869";

        airline.setTelephone(telephone);

        assertEquals(telephone, airline.getTelephone());
    }
}
