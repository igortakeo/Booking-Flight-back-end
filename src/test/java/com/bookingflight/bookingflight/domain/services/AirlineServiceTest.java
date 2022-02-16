package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Airline;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.AirlineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AirlineServiceTest {

    AirlineService airlineService;

    @Autowired
    AirlineRepository airlineRepository;

    @BeforeEach
    public void setUp(){
        this.airlineService = new AirlineService(airlineRepository);
    }

    @Test
    void findByCode() {

        final Airline airlineTest = new Airline(
                "codeTest",
                "nameTest",
                10,
                "test@gmail.com",
                "79984133545"
        );

        airlineService.create(airlineTest);

        Airline airlineReturnTest = airlineService.findByCode(airlineTest.getCode());

        assertEquals(airlineTest.getCode(), airlineReturnTest.getCode());
        assertEquals(airlineTest.getName(), airlineReturnTest.getName());
        assertEquals(airlineTest.getNumberPlanes(), airlineReturnTest.getNumberPlanes());
        assertEquals(airlineTest.getEmail(), airlineReturnTest.getEmail());
        assertEquals(airlineTest.getTelephone(), airlineReturnTest.getTelephone());
    }

    @Test
    void findByCodeAirlineNotFound(){

        assertThrows(ObjectNotFoundException.class, ()-> airlineService.findByCode("codeTest"));
    }

    @Test
    void createAirlineAlreadyExist(){

        final Airline airlineTest = new Airline(
                "codeTest",
                "nameTest",
                10,
                "test@gmail.com",
                "79984133545"
        );

        airlineService.create(airlineTest);

        assertThrows(ObjectAlreadyExistException.class, () -> airlineService.create(airlineTest));
    }

    @Test
    void findAll() {
        final Airline airlineTest1 = new Airline(
                "codeTest1",
                "nameTest1",
                10,
                "test@gmail.com",
                "79984133545"
        );

        final Airline airlineTest2 = new Airline(
                "codeTest2",
                "nameTest2",
                10,
                "test@gmail.com",
                "79984133545"
        );

        final Airline airlineTest3 = new Airline(
                "codeTest3",
                "nameTest3",
                10,
                "test@gmail.com",
                "79984133545"
        );

        final List<Airline> airlineTestList = new ArrayList<>(Arrays.asList(
                airlineTest1,
                airlineTest2,
                airlineTest3
        ));

        for(Airline airline : airlineTestList){
            airlineService.create(airline);
        }

        List<Airline> airlineReturnList = airlineService.findAll();

        assertEquals(airlineTestList.size(), airlineReturnList.size());

        for(int i = 0; i<airlineReturnList.size(); i++){
            assertEquals(airlineTestList.get(i), airlineReturnList.get(i)); //Class need override equals method
        }
    }

    @Test
    void update() {

        final Airline airlineTest = new Airline(
                "codeTest",
                "nameTest",
                10,
                "test@gmail.com",
                "79984133545"
        );

        final Airline airlineTestUpdate = new Airline(
                "codeTest",
                "nameTest12",
                112,
                "test@gmail.com",
                "79984133545"
        );

        airlineService.create(airlineTest);

        airlineService.update(airlineTest.getCode(), airlineTestUpdate);

        Airline airlineReturnTest = airlineService.findByCode(airlineTest.getCode());

        assertEquals(airlineTestUpdate.getCode(), airlineReturnTest.getCode());
        assertEquals(airlineTestUpdate.getName(), airlineReturnTest.getName());
        assertEquals(airlineTestUpdate.getNumberPlanes(), airlineReturnTest.getNumberPlanes());
        assertEquals(airlineTestUpdate.getEmail(), airlineReturnTest.getEmail());
        assertEquals(airlineTestUpdate.getTelephone(), airlineReturnTest.getTelephone());
    }

    @Test
    void delete() {

        final Airline airlineTest = new Airline(
                "codeTest",
                "nameTest",
                10,
                "test@gmail.com",
                "79984133545"
        );

        airlineService.create(airlineTest);

        airlineService.delete(airlineTest.getCode());

        assertThrows(ObjectNotFoundException.class, () -> airlineService.findByCode(airlineTest.getCode()));
    }
}