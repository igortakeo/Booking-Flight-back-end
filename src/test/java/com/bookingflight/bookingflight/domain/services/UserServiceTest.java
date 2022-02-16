package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.User;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserServiceTest {


    UserService userService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        this.userService = new UserService(userRepository);
    }

    @Test
    void findById() {
        final String userPasswordTest = bCryptPasswordEncoder.encode("1234");

        final User userTest = new User(
                1L,
                "nameTest",
                "usernameTest",
                userPasswordTest.toString(),
                true);

        userService.create(userTest);

        final User userReturnTest = userService.findById(userTest.getId());

        assertEquals(userTest.getId(), userReturnTest.getId());
        assertEquals(userTest.getName(), userReturnTest.getName());
        assertEquals(userTest.getUsername(), userReturnTest.getUsername());
        assertEquals(userTest.getPassword(), userReturnTest.getPassword());
        assertTrue(userReturnTest.isAdmin());
    }

    @Test
    void findByIdUserNotFound(){

        assertThrows(ObjectNotFoundException.class, ()-> userService.findById(1L));
    }

    @Test
    void create() {

        final String userPasswordTest = bCryptPasswordEncoder.encode("1234");

        final User userTest = new User(
                13L,
                "nameTest",
                "usernameTest",
                userPasswordTest.toString(),
                true);

        User userReturn = userService.create(userTest);

        User userCreated = userService.findById(userReturn.getId());

        assertEquals(userReturn.getId(), userCreated.getId());
        assertEquals(userReturn.getName(), userCreated.getName());
        assertEquals(userReturn.getUsername(), userCreated.getUsername());
        assertEquals(userReturn.getPassword(), userCreated.getPassword());
        assertEquals(userReturn.isAdmin(), userCreated.isAdmin());
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }
}