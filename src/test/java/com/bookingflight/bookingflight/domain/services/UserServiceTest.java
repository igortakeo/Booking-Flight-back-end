package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.User;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserServiceTest {

    UserService userService;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void intiMocks(){
        MockitoAnnotations.initMocks(this);

        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
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

        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(userTest));

        final User userReturnTest = userRepository.findById(1L).orElseThrow();

        assertEquals(userTest.getId(), userReturnTest.getId());
        assertEquals(userTest.getName(), userReturnTest.getName());
        assertEquals(userTest.getUsername(), userReturnTest.getUsername());
        assertEquals(userTest.getPassword(), userReturnTest.getPassword());
        assertTrue(userReturnTest.isAdmin());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdUserNotFound(){
        when(userRepository.findById(eq(1L))).thenThrow(new ObjectNotFoundException("Object not found with Id = 1"));

        assertThrows(ObjectNotFoundException.class, ()-> userRepository.findById(1L).orElseThrow());
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }
}