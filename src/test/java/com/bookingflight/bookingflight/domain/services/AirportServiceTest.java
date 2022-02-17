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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        final Airport airportTest1 = new Airport(
                null,
                "airportTest1",
                "streetTest1",
                10,
                "cepTest",
                "cityTest"
        );

        final Airport airportTest2 = new Airport(
                null,
                "airportTest2",
                "streetTest2",
                10,
                "cepTest",
                "cityTest"
        );

        final Airport airportTest3 = new Airport(
                null,
                "airportTest3",
                "streetTest3",
                10,
                "cepTest",
                "cityTest"
        );

        List<Airport> airportTestList= new ArrayList<>(Arrays.asList(
                airportTest1,
                airportTest2,
                airportTest3
        ));

        for(Airport airport : airportTestList){
            airportService.create(airport);
        }

        List<Airport> airportReturnList = airportService.findAll();

        assertEquals(airportTestList.size(), airportReturnList.size());

        for(int i=0; i<airportReturnList.size(); i++){
            assertEquals(airportTestList.get(i), airportReturnList.get(i));
        }
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