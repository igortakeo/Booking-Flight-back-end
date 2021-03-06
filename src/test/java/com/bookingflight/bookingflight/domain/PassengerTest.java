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

class PassengerTest {

    private Passenger passenger;

    @BeforeEach
    public void setUp(){

        this.passenger = new Passenger(
                "95466724084",
                "nameTest",
                "lastNameTest",
                22,
                "emailTest",
                "telephoneTest"
        );
    }

    @Test
    void getCpf() {
        final String cpf = "95466724084";

        passenger.setCpf(cpf);

        assertEquals(cpf, passenger.getCpf());
    }

    @Test
    void getName() {
        final String name = "nameTest";

        passenger.setName(name);

        assertEquals(name, passenger.getName());
    }

    @Test
    void getLastName() {
        final String lastName = "lastNameTest";

        passenger.setLastName(lastName);

        assertEquals(lastName, passenger.getLastName());
    }

    @Test
    void getAge() {
        final Integer age = 22;

        passenger.setAge(age);

        assertEquals(age, passenger.getAge());
    }

    @Test
    void getEmail() {
        final String email = "email_teste@gmail.com";

        passenger.setEmail(email);

        assertEquals(email, passenger.getEmail());
    }

    @Test
    void getTelephone() {
        final String telephone = "4399795869";

        passenger.setTelephone(telephone);

        assertEquals(telephone, passenger.getTelephone());
    }

    @Test
    void getBookings() {
        final Flight flight = new Flight(
                null,
                "sourceTest",
                "targetTest",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final ClassFlight classFlight1 = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("550.00"),
                flight
        );

        final ClassFlight classFlight2 = new ClassFlight(
                ClassTravelEnum.BUSINESS,
                new BigDecimal("250.00"),
                flight
        );

        final Booking booking1 = new Booking(
                this.passenger,
                flight,
                ClassTravelEnum.FIRST_CLASS,
                91,
                classFlight1
        );

        final Booking booking2 = new Booking(
                this.passenger,
                flight,
                ClassTravelEnum.BUSINESS,
                23,
                classFlight2
        );

        final List<Booking> bookingList = new ArrayList<>(Arrays.asList(booking1, booking2));

        passenger.setBookings(bookingList);

        List<Booking> bookingListReturn = passenger.getBookings();

        for(int i=0; i<bookingListReturn.size(); i++){
            Booking booking = bookingListReturn.get(i);
            assertEquals(bookingList.get(i), booking);
        }
    }
}