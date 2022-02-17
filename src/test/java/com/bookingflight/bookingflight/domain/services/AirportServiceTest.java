package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Airport;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.AirlineRepository;
import com.bookingflight.bookingflight.repositories.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AirportServiceTest {

    AirportService airportService;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AirlineRepository airlineRepository;

    @BeforeEach
    void setUp() {

        this.airportService = new AirportService(
                airportRepository,
                airlineRepository
        );
    }

    @Test
    void findById() {

        final Airport airportTest = new Airport(
                null,
                "airportTest",
                "streetTest",
                10,
                "cepTest",
                "cityTest"
        );

        airportService.create(airportTest);

        Airport airportReturn = airportService.findById(airportTest.getId());

        assertEquals(airportTest.getId(), airportReturn.getId());
        assertEquals(airportTest.getName(), airportReturn.getName());
        assertEquals(airportTest.getStreet(), airportReturn.getStreet());
        assertEquals(airportTest.getNumber(), airportReturn.getNumber());
        assertEquals(airportTest.getCep(), airportReturn.getCep());
        assertEquals(airportTest.getCity(), airportReturn.getCity());
    }

    @Test
    void findByIdAirportNotFound(){

        assertThrows(ObjectNotFoundException.class, () -> airportService.findById(1L));
    }

    @Test
    void createAirportAlreadyExist(){
        final Airport airportTest = new Airport(
                null,
                "airportTest",
                "streetTest",
                10,
                "cepTest",
                "cityTest"
        );

        airportService.create(airportTest);

        assertThrows(ObjectAlreadyExistException.class, () -> airportService.create(airportTest));
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAirlines() {
    }

    @Test
    void addAirline() {
    }
}