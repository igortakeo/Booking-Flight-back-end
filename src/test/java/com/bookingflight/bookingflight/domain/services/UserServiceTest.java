package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.User;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotAllowedToBeDeletedException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.UserRepository;
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
class UserServiceTest {


    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        this.userService = new UserService(userRepository);
    }

    @Test
    void findById() {

        final User userTest = new User(
                1L,
                "nameTest",
                "usernameTest",
                "1234",
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
    void createUserAlreadyExist(){

        final User userTest = new User(
                null,
                "nameTest",
                "usernameTest",
                "1234",
                true);

        User userReturn = userService.create(userTest);

        assertThrows(ObjectAlreadyExistException.class, ()-> userService.create(userTest));
    }

    @Test
    void delete() {

        final User userTest = new User(
                null,
                "nameTest",
                "usernameTest",
                "1234",
                true);

        User userReturn = userService.create(userTest);

        userService.delete(userReturn.getId());

        assertThrows(ObjectNotFoundException.class, () -> userService.findById(userReturn.getId()));
    }

    @Test
    void deleteUserNotAllowedToDelete(){

        final User userTest = new User(
                null,
                "nameTest",
                "igortakeo",
                "1234",
                true);

        User userReturn = userService.create(userTest);

        assertThrows(ObjectNotAllowedToBeDeletedException.class, () -> userService.delete(userReturn.getId()));
    }

    @Test
    void findAll() {

        final User userTest1 = new User(
                null,
                "nameTest1",
                "usernameTest1",
                "1234",
                true);

        final User userTest2 = new User(
                null,
                "nameTest2",
                "usernameTest2",
                "12345",
                false);


        final User userTest3 = new User(
                null,
                "nameTest3",
                "usernameTest3",
                "654",
                false);

        final List<User> userTestList = new ArrayList<>(Arrays.asList(
                userTest1,
                userTest2,
                userTest3
        ));

        for(User user : userTestList){
            userService.create(user);
        }

        List<User> userReturnList = userService.findAll();

        assertEquals(userTestList.size(), userReturnList.size());

        for(int i=0; i < userReturnList.size(); i++) {
            assertEquals(userTestList.get(i), userReturnList.get(i));
        }
    }
}