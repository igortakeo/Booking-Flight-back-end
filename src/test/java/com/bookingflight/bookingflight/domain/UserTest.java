package com.bookingflight.bookingflight.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    public void setUp(){
        this.user = new User();
    }

    @Test
    void getId() {
        final Long id = 1L;

        user.setId(id);

        assertEquals(id, user.getId());
    }

    @Test
    void getName() {
        final String name = "nameTest";

        user.setName(name);

        assertEquals(name, user.getName());
    }

    @Test
    void getUsername() {
        final String username = "usernameTest";

        user.setUsername(username);

        assertEquals(username, user.getUsername());
    }

    @Test
    void getPassword() {
        final String password = "passwordTest";

        user.setPassword(password);

        assertEquals(password, user.getPassword());
    }

    @Test
    void isAdmin() {
        final boolean isAdmin = true;

        user.setAdmin(isAdmin);

        assertEquals(isAdmin, user.isAdmin());
    }
}