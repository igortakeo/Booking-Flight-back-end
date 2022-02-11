package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    void getBookings() {
        final Passenger passenger1 = new Passenger(
                "44420130040",
                "nameTest",
                "lastNameTest",
                22,
                "email-teste@gmail.com",
                "telephoneTest"
        );

        final Passenger passenger2 = new Passenger(
                "54420130040",
                "nameTest2",
                "lastNameTest2",
                23,
                "email-teste2@gmail.com",
                "telephoneTest2"
        );

        final Flight flight = new Flight(
                null,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Booking booking1 = new Booking(
                passenger1,
                flight,
                ClassTravelEnum.FIRST_CLASS,
                65,
                this.classFlight
        );

        final Booking booking2 = new Booking(
                passenger2,
                flight,
                ClassTravelEnum.ECONOMY,
                25,
                this.classFlight
        );

        final List<Booking> bookingList = new ArrayList<>(Arrays.asList(booking1, booking2));

        classFlight.setBookings(bookingList);

        List<Booking> bookingListReturn = classFlight.getBookings();

        for(int i=0; i<bookingListReturn.size(); i++){
            Booking booking = bookingListReturn.get(i);
            assertEquals(bookingList.get(i), booking);
        }
    }
}