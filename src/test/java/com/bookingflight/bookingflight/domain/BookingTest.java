package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

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
    void getClassFlight() {
        final ClassTravelEnum classFlight = ClassTravelEnum.ECONOMY;

        booking.setClassFlight(classFlight);

        assertEquals(classFlight, booking.getClassFlight());
    }
}