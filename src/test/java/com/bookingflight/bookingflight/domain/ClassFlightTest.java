package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassFlightTest {

    private ClassFlight classFlight;

    @BeforeEach
    public void setUp(){
        this.classFlight = new ClassFlight();
    }

    @Test
    void getClassTravel() {
        final ClassTravelEnum classTravel = ClassTravelEnum.BUSINESS;

        classFlight.setClassTravel(classTravel);

        assertEquals(classTravel, classFlight.getClassTravel());
    }

    @Test
    void getPrice() {
        final BigDecimal price = new BigDecimal("540.00");

        classFlight.setPrice(price);

        assertEquals(price, classFlight.getPrice());
    }

    @Test
    void getFlight() {
        final Flight flight = new Flight(
                null,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        classFlight.setFlight(flight);

        assertEquals(flight, classFlight.getFlight());
    }


    @Test
    void getBooking() {
        final Passenger passenger = new Passenger(
                "44420130040",
                "nameTest",
                "lastNameTest",
                22,
                "email-teste@gmail.com",
                "telephoneTest"
        );

        final Flight flight = new Flight(
                null,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final ClassFlight classFlight = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("550.00"),
                flight
        );

        final Booking booking = new Booking(
                passenger,
                flight,
                ClassTravelEnum.FIRST_CLASS,
                65,
                classFlight
        );

        classFlight.setBooking(booking);

        assertEquals(booking, classFlight.getBooking());
    }
}