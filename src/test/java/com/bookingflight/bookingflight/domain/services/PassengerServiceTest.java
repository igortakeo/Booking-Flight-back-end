package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Passenger;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.PassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PassengerServiceTest {

    @InjectMocks
    PassengerService passengerService;

    @Mock
    PassengerRepository passengerRepository;

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findByCpf() {
        Passenger passengerTest = new Passenger(
                "cpfTest",
                "nameTest",
                "lastNameTest",
                22,
                "emailTest",
                "telephoneTest"
        );

        when(passengerRepository.save(eq(passengerTest))).thenReturn(passengerTest);

        passengerTest = passengerService.create(passengerTest);

        when(passengerRepository.findByCpf(eq(passengerTest.getCpf()))).thenReturn(Optional.of(passengerTest));

        Passenger passengerReturnTest = passengerService.findByCpf(passengerTest.getCpf());

        assertEquals(passengerTest.getCpf(), passengerReturnTest.getCpf());
        assertEquals(passengerTest.getName(), passengerReturnTest.getName());
        assertEquals(passengerTest.getLastName(), passengerReturnTest.getLastName());
        assertEquals(passengerTest.getAge(), passengerReturnTest.getAge());
        assertEquals(passengerTest.getEmail(), passengerReturnTest.getEmail());
        assertEquals(passengerTest.getTelephone(), passengerReturnTest.getTelephone());
        verify(passengerRepository, times(1)).save(any());
        verify(passengerRepository, times(1)).findByCpf(any());
    }

    @Test
    void findByCpfPassengerNotFound(){
        when(passengerRepository.findByCpf(eq("cpfTest"))).thenThrow(ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class, () -> passengerService.findByCpf("cpfTest"));
        verify(passengerRepository, times(1)).findByCpf(any());
    }

  /*  @Test
    void findAll() {
        final Passenger passengerTest1 = new Passenger(
                "cpfTest1",
                "nameTest1",
                "lastNameTest1",
                22,
                "emailTest",
                "telephoneTest"
        );

        final Passenger passengerTest2 = new Passenger(
                "cpfTest2",
                "nameTest2",
                "lastNameTest2",
                22,
                "emailTest",
                "telephoneTest"
        );

        final Passenger passengerTest3 = new Passenger(
                "cpfTest3",
                "nameTest3",
                "lastNameTest3",
                22,
                "emailTest",
                "telephoneTest"
        );

        final List<Passenger> passengerTestList = new ArrayList<>(Arrays.asList(
                passengerTest1,
                passengerTest2,
                passengerTest3
        ));

        when(passengerRepository.findAll()).thenReturn(passengerTestList);

        List<Passenger> passengerReturnList = passengerService.findAll();

        assertEquals(passengerTestList.size(), passengerReturnList.size());

        for(int i=0; i<passengerReturnList.size(); i++){
            assertEquals(passengerTestList.get(i), passengerReturnList.get(i));
        }

        verify(passengerRepository, times(1)).findAll();
    }*/


    @Test
    void update() {
        Passenger passengerTest = new Passenger(
                "cpfTest",
                "nameTest",
                "lastNameTest",
                22,
                "emailTest",
                "telephoneTest"
        );

        final Passenger passengerTestUpdate = new Passenger(
                "cpfTest",
                "nameTest1",
                "lastNameTest1",
                22,
                "emailTest",
                "telephoneTest"
        );

        when(passengerRepository.save(eq(passengerTest))).thenReturn(passengerTest);

        passengerTest = passengerService.create(passengerTest);

        when(passengerRepository.save(eq(passengerTestUpdate))).thenReturn(passengerTestUpdate);
        when(passengerRepository.findByCpf(passengerTest.getCpf())).thenReturn(Optional.of(passengerTest));

        Passenger passengerReturnTest = passengerService.update(passengerTest.getCpf(), passengerTestUpdate);

        assertEquals(passengerTestUpdate.getCpf(), passengerReturnTest.getCpf());
        assertEquals(passengerTestUpdate.getName(), passengerReturnTest.getName());
        assertEquals(passengerTestUpdate.getLastName(), passengerReturnTest.getLastName());
        assertEquals(passengerTestUpdate.getAge(), passengerReturnTest.getAge());
        assertEquals(passengerTestUpdate.getEmail(), passengerReturnTest.getEmail());
        assertEquals(passengerTestUpdate.getTelephone(), passengerReturnTest.getTelephone());
        verify(passengerRepository, times(2)).save(any());
        verify(passengerRepository, times(1)).findByCpf(any());
    }

    @Test
    void delete() {
        Passenger passengerTest = new Passenger(
                "cpfTest",
                "nameTest",
                "lastNameTest",
                22,
                "emailTest",
                "telephoneTest"
        );

        when(passengerRepository.save(eq(passengerTest))).thenReturn(passengerTest);

        passengerTest = passengerService.create(passengerTest);

        when(passengerRepository.findByCpf(passengerTest.getCpf())).thenReturn(Optional.of(passengerTest));

        doNothing().when(passengerRepository).delete(passengerTest);

        passengerService.delete(passengerTest.getCpf());

        verify(passengerRepository, times(1)).save(any());
        verify(passengerRepository, times(1)).findByCpf(any());
        verify(passengerRepository, times(1)).delete(any());
    }
}