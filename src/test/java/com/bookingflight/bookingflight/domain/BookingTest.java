package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingTest {

    private Booking booking;

    @BeforeEach
    public void setUp(){
        this.booking = new Booking();
    }

    @Test
    void getPassenger() {
        final Passenger passenger = new Passenger(
                "44420130040",
                "nameTest",
                "lastNameTest",
                22,
                "email-teste@gmail.com",
                "telephoneTest"
        );

        booking.setPassenger(passenger);

        assertEquals(passenger,booking.getPassenger());
    }

    @Test
    void getFlight() {
        final Flight flight = new Flight(
                null,
                "sourceTest",
                "targetTest",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        booking.setFlight(flight);

        assertEquals(flight, booking.getFlight());
    }

    @Test
    void getClassTravel() {
        final ClassTravelEnum classTravel = ClassTravelEnum.BUSINESS;

        booking.setClassTravel(classTravel);

        assertEquals(classTravel, booking.getClassTravel());
    }

    @Test
    void getSeat() {
        final Integer seat = 98;

        booking.setSeat(seat);

        assertEquals(seat, booking.getSeat());
    }

    @Test
    void getClassFlight() {
        final Flight flight = new Flight();
        final ClassFlight classFlight = new ClassFlight(
                ClassTravelEnum.BUSINESS,
                new BigDecimal("240.00"),
                flight
        );

        booking.setClassFlight(classFlight);

        assertEquals(classFlight, booking.getClassFlight());
    }
}