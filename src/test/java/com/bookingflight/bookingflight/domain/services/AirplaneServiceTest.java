package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Airline;
import com.bookingflight.bookingflight.domain.Airplane;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.AirplaneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AirplaneServiceTest {

    @InjectMocks
    AirplaneService airplaneService;

    @Mock
    AirplaneRepository airplaneRepository;

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById() {
        Airplane airplaneTest = new Airplane(
                1L,
                "nameTest",
                52
        );

        final Airplane airplaneCreatedTest = new Airplane(
                1L,
                "nameTest",
                52
        );

        when(airplaneRepository.save(eq(airplaneTest))).thenReturn(airplaneCreatedTest);

        airplaneTest = airplaneService.create(airplaneTest);

        when(airplaneRepository.findById(eq(1L))).thenReturn(Optional.of(airplaneTest));

        Airplane airplaneReturnTest = airplaneService.findById(airplaneTest.getId());

        assertEquals(airplaneTest.getId(), airplaneReturnTest.getId());
        assertEquals(airplaneTest.getName(), airplaneReturnTest.getName());
        assertEquals(airplaneTest.getMaximumNumberPassengers(), airplaneReturnTest.getMaximumNumberPassengers());
        verify(airplaneRepository, times(1)).findById(eq(1L));
    }

    @Test
    void findByIdAirplaneNotFound(){
        when(airplaneRepository.findById(1L)).thenThrow(ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class, () -> airplaneService.findById(1L));
        verify(airplaneRepository, times(1)).findById(eq(1L));
    }

    @Test
    void findAll() {
        final Airplane airplaneTest1 = new Airplane(
                null,
                "nameTest1",
                52
        );

        final Airplane airplaneTest2 = new Airplane(
                null,
                "nameTest2",
                52
        );

        final Airplane airplaneTest3 = new Airplane(
                null,
                "nameTest3",
                52
        );


        final List<Airplane> airplaneTestList = new ArrayList<>(Arrays.asList(
                airplaneTest1,
                airplaneTest2,
                airplaneTest3
        ));

        when(airplaneRepository.findAll()).thenReturn(airplaneTestList);

        List<Airplane> airplaneReturnList = airplaneRepository.findAll();

        assertEquals(airplaneTestList.size(), airplaneReturnList.size());

        for(int i=0; i<airplaneReturnList.size(); i++){
            assertEquals(airplaneTestList.get(i), airplaneReturnList.get(i));
        }

        verify(airplaneRepository, times(1)).findAll();
    }

    @Test
    void create() {
        final Airplane airplaneTest = new Airplane(
                1L,
                "nameTest1",
                52
        );

        when(airplaneRepository.save(eq(airplaneTest))).thenReturn(airplaneTest);

        Airplane airplaneReturnTest = airplaneService.create(airplaneTest);

        assertEquals(airplaneTest.getId(), airplaneReturnTest.getId());
        assertEquals(airplaneTest.getName(), airplaneReturnTest.getName());
        assertEquals(airplaneTest.getMaximumNumberPassengers(), airplaneReturnTest.getMaximumNumberPassengers());
        verify(airplaneRepository, times(1)).save(eq(airplaneTest));
    }

    @Test
    void createAirplaneAlreadyExist(){
        final Airplane airplaneTest = new Airplane(
                1L,
                "nameTest1",
                52
        );


        when(airplaneRepository.save(eq(airplaneTest))).thenReturn(airplaneTest);

        airplaneService.create(airplaneTest);

        when(airplaneRepository.save(eq(airplaneTest))).thenThrow(ObjectAlreadyExistException.class);

        assertThrows(ObjectAlreadyExistException.class, () -> airplaneService.create(airplaneTest));
        verify(airplaneRepository, times(2)).save(eq(airplaneTest));
    }

    @Test
    void update() {
        Airplane airplaneTest = new Airplane(
                1L,
                "nameTest1",
                52
        );

        final Airplane airplaneTestUpdate = new Airplane(
                1L,
                "nameTest2",
                53
        );

        final Airplane airplanCreatedTest = new Airplane(
                1L,
                "nameTest1",
                53
        );

        when(airplaneRepository.save(eq(airplaneTest))).thenReturn(airplanCreatedTest);

        airplaneTest = airplaneService.create(airplaneTest);

        when(airplaneRepository.save(eq(airplaneTestUpdate))).thenReturn(airplaneTestUpdate);
        when(airplaneRepository.findById(eq(1L))).thenReturn(Optional.of(airplaneTest));
        when(airplaneRepository.findByName(eq(airplaneTestUpdate.getName()))).thenReturn(null);

        Airplane airplaneReturnTest = airplaneService.update(airplaneTestUpdate.getId(), airplaneTestUpdate);

        assertEquals(airplaneTestUpdate.getId(), airplaneReturnTest.getId());
        assertEquals(airplaneTestUpdate.getName(), airplaneReturnTest.getName());
        assertEquals(airplaneTestUpdate.getMaximumNumberPassengers(), airplaneReturnTest.getMaximumNumberPassengers());
        verify(airplaneRepository, times(2)).save(any());
        verify(airplaneRepository, times(1)).findById(1L);
        verify(airplaneRepository, times(2)).findByName(any());
    }

    @Test
    void updateAirplaneAlreadyExist(){
        Airplane airplaneTest1 = new Airplane(
                1L,
                "nameTest1",
                52
        );

        Airplane airplaneTest2 = new Airplane(
                2L,
                "nameTest2",
                53
        );

        final Airplane airplaneTestUpdate = new Airplane(
                1L,
                "nameTest2",
                53
        );

        final Airplane airplaneCreatedTest1 = new Airplane(
                1L,
                "nameTest1",
                52
        );

        final Airplane airplaneCreatedTest2 = new Airplane(
                2L,
                "nameTest2",
                53
        );

        when(airplaneRepository.save(eq(airplaneTest1))).thenReturn(airplaneCreatedTest1);

        airplaneTest1 = airplaneService.create(airplaneTest1);

        when(airplaneRepository.save(eq(airplaneTest2))).thenReturn(airplaneCreatedTest2);

        airplaneTest2 = airplaneService.create(airplaneTest2);

        when(airplaneRepository.findById(eq(airplaneTestUpdate.getId()))).thenReturn(Optional.ofNullable(airplaneTest1));
        when(airplaneRepository.findByName(eq(airplaneTestUpdate.getName()))).thenReturn(airplaneTest2);

        assertThrows(ObjectAlreadyExistException.class, () -> airplaneService.update(airplaneTestUpdate.getId(), airplaneTestUpdate));
        verify(airplaneRepository, times(2)).save(any());
        verify(airplaneRepository, times(1)).findById(1L);
        verify(airplaneRepository, times(3)).findByName(any());
    }

    @Test
    void delete() {
        final Airline airlineTest = new Airline(
                "codeTest",
                "nameTest",
                10,
                "test@gmail.com",
                "79984133545"
        );

        Airplane airplaneTest = new Airplane(
                1L,
                "nameTest",
                52
        );

        final Airplane airplaneCreatedTest = new Airplane(
                1L,
                "nameTest",
                52
        );

        airplaneTest.setAirline(airlineTest);
        airplaneCreatedTest.setAirline(airlineTest);

        when(airplaneRepository.findById(eq(airplaneTest.getId()))).thenReturn(Optional.of(airplaneTest));
        when(airplaneRepository.save(eq(airplaneTest))).thenReturn(airplaneCreatedTest);

        airplaneTest = airplaneService.create(airplaneTest);

        doNothing().when(airplaneRepository).deleteById(eq(airplaneTest.getId()));

        airplaneService.delete(airplaneTest.getId());
        verify(airplaneRepository, times(1)).save(any());
        verify(airplaneRepository, times(1)).findByName(any());
        verify(airplaneRepository, times(1)).deleteById(any());

    }
}