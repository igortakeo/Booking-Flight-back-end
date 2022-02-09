package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void getAirports() {
        final List<Airport> airportList = new ArrayList<>(Arrays.asList(
                new Airport(
                        null,
                        "Guarulhos",
                        "streetTest",
                        10,
                        "cepTest",
                        "cityTest"),
                new Airport(
                        null,
                        "Viracopos",
                        "streetTest1",
                        11,
                        "cepTest1",
                        "cityTest1")
                )
        );

        airline.setAirports(airportList);

        List<Airport> airportListReturn = airline.getAirports();

        for(int i=0; i<airportListReturn.size(); i++){
            Airport airport = airportListReturn.get(i);
            assertEquals(airport, airportList.get(i));
        }
    }

    @Test
    void getAirplanes() {
        final Airplane airplaneTest1 = new Airplane(null, "airplaneTest1", 30);
        final Airplane airplaneTest2 = new Airplane(null, "airplaneTest2", 50);

        final List<Airplane> airplaneList = new ArrayList<>(Arrays.asList(airplaneTest1, airplaneTest2));

        airline.setAirplanes(airplaneList);

        List<Airplane> airplaneListReturn = airline.getAirplanes();

        for(int i=0; i<airplaneListReturn.size(); i++){
            Airplane airplane = airplaneListReturn.get(i);
            assertEquals(airplane, airplaneListReturn.get(i));
        }
    }
}
