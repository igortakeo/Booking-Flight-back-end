package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PassengerTest {

    private Passenger passenger;

    @BeforeEach
    public void setUp(){
        this.passenger = new Passenger();
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

        assertEquals("das", passenger.getLastName());
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
}