package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Airplane;
import com.bookingflight.bookingflight.domain.Airport;
import com.bookingflight.bookingflight.domain.Flight;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class FlightServiceTest {

    @InjectMocks
    FlightService flightService;

    @Mock
    FlightRepository flightRepository;

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById() {
        final Airport airportTest = new Airport(
                1L,
                "airportTest",
                "streetTest",
                10,
                "cepTest",
                "cityTest"
        );

        final Airplane airplaneTest = new Airplane(
                1L,
                "nameTest",
                52
        );

        Flight flightTest = new Flight(
                null,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flightCreatedTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        when(flightRepository.save(eq(flightTest))).thenReturn(flightCreatedTest);

        flightTest = flightService.create(flightTest);

        when(flightRepository.findById(eq(flightTest.getId()))).thenReturn(Optional.of(flightTest));

        Flight flightReturnTest = flightService.findById(flightTest.getId());

        assertEquals(flightTest.getId(), flightReturnTest.getId());
        assertEquals(flightTest.getAirport(), flightReturnTest.getAirport());
        assertEquals(flightTest.getAirplane(), flightReturnTest.getAirplane());
        assertEquals(flightTest.getSource(), flightReturnTest.getSource());
        assertEquals(flightTest.getTarget(), flightReturnTest.getTarget());
        assertEquals(flightTest.getDate(), flightReturnTest.getDate());
        verify(flightRepository, times(1)).save(any());
        verify(flightRepository, times(1)).findById(any());
        verify(flightRepository, times(1)).findSameFlight(any(), any(), any(), any(), any());

    }

    @Test
    void findByIdFlightNotFound(){
        when(flightRepository.findById(eq(1L))).thenThrow(ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class, () -> flightService.findById(1L));
        verify(flightRepository, times(1)).findById(any());
    }

    @Test
    void findAll() {
        final Airport airportTest = new Airport(
                1L,
                "airportTest",
                "streetTest",
                10,
                "cepTest",
                "cityTest"
        );

        final Airplane airplaneTest = new Airplane(
                1L,
                "nameTest",
                52
        );

        final Flight flightTest1 = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flightTest2 = new Flight(
                2L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Belo Horizonte",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flightTest3 = new Flight(
                3L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Curitiba",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final List<Flight> flightTestList = new ArrayList<>(Arrays.asList(
                flightTest1,
                flightTest2,
                flightTest3
        ));

        when(flightRepository.findAll()).thenReturn(flightTestList);

        List<Flight> flightReturnTestList = flightService.findAll();

        assertEquals(flightTestList.size(), flightReturnTestList.size());

        for(int i=0; i<flightReturnTestList.size(); i++){
            assertEquals(flightTestList.get(i), flightReturnTestList.get(i));
        }

        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void createFlightAlreadyExist() {
        final Airport airportTest = new Airport(
                1L,
                "airportTest",
                "streetTest",
                10,
                "cepTest",
                "cityTest"
        );

        final Airplane airplaneTest = new Airplane(
                1L,
                "nameTest",
                52
        );

        Flight flightTest1 = new Flight(
                null,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flightTest2 = new Flight(
                2L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flightCreatedTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        when(flightRepository.save(eq(flightTest1))).thenReturn(flightCreatedTest);

        flightTest1 = flightService.create(flightTest1);

        when(flightRepository.findSameFlight(
                eq("Sao Paulo"),
                eq("Rio de Janeiro"),
                eq(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)),
                eq(airplaneTest.getId()),
                eq(airportTest.getId())
        )).thenReturn(flightTest1);

        assertThrows(ObjectAlreadyExistException.class, () -> flightService.create(flightTest2));

        verify(flightRepository, times(1)).save(any());
        verify(flightRepository, times(2)).findSameFlight(any(), any(), any(), any(), any());
    }

    @Test
    void update(){
        final Airport airportTest = new Airport(
                1L,
                "airportTest",
                "streetTest",
                10,
                "cepTest",
                "cityTest"
        );

        final Airplane airplaneTest = new Airplane(
                1L,
                "nameTest",
                52
        );

        Flight flightTest1 = new Flight(
                null,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flightCreatedTest1 = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

       final Flight flightTestUpdate = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Curitiba",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        when(flightRepository.save(eq(flightTest1))).thenReturn(flightCreatedTest1);

        flightTest1 = flightService.create(flightTest1);

        when(flightRepository.findById(eq(flightTest1.getId()))).thenReturn(Optional.of(flightTest1));
        when(flightRepository.save(eq(flightTestUpdate))).thenReturn(flightTestUpdate);

        Flight flightTestUpdateReturn = flightService.update(flightTest1.getId(), flightTestUpdate);

        assertEquals(flightTest1.getId(), flightTestUpdateReturn.getId());
        assertEquals(flightTest1.getAirport(), flightTestUpdateReturn.getAirport());
        assertEquals(flightTest1.getAirplane(), flightTestUpdateReturn.getAirplane());
        assertEquals(flightTest1.getSource(), flightTestUpdateReturn.getSource());
        assertEquals(flightTest1.getTarget(), flightTestUpdateReturn.getTarget());
        assertEquals(flightTest1.getDate(), flightTestUpdateReturn.getDate());
        verify(flightRepository, times(2)).save(any());
        verify(flightRepository, times(1)).findById(any());
        verify(flightRepository, times(2)).findSameFlight(any(), any(), any(), any(), any());
    }

    @Test
    void updateFlightAlreadyExist() {
        final Airport airportTest = new Airport(
                1L,
                "airportTest",
                "streetTest",
                10,
                "cepTest",
                "cityTest"
        );

        final Airplane airplaneTest = new Airplane(
                1L,
                "nameTest",
                52
        );

        Flight flightTest1 = new Flight(
                null,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        Flight flightTest2 = new Flight(
                null,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Belo Horizonte",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flightTestUpdate = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Belo Horizonte",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flightCreatedTest1 = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flightCreatedTest2 = new Flight(
                2L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Belo Horizonte",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        when(flightRepository.save(eq(flightTest1))).thenReturn(flightCreatedTest1);

        flightTest1 = flightService.create(flightTest1);

        when(flightRepository.save((eq(flightTest2)))).thenReturn(flightCreatedTest2);

        flightTest2 = flightService.create(flightTest2);

        when(flightRepository.findById(eq(flightCreatedTest1.getId()))).thenReturn(Optional.ofNullable(flightTest1));

        when(flightRepository.findSameFlight(
                eq("Sao Paulo"),
                eq("Belo Horizonte"),
                eq(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)),
                eq(airplaneTest.getId()),
                eq(airportTest.getId())
        )).thenReturn(flightTest2);

        assertThrows(ObjectAlreadyExistException.class, ()-> flightService.update(flightCreatedTest1.getId(), flightTestUpdate));

        verify(flightRepository, times(2)).save(any());
        verify(flightRepository, times(1)).findById(any());
        verify(flightRepository, times(3)).findSameFlight(any(), any(), any(), any(), any());
    }

    @Test
    void delete() {
        final Airport airportTest = new Airport(
                1L,
                "airportTest",
                "streetTest",
                10,
                "cepTest",
                "cityTest"
        );

        final Airplane airplaneTest = new Airplane(
                1L,
                "nameTest",
                52
        );

        Flight flightTest = new Flight(
                null,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final Flight flightCreatedTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        when(flightRepository.save(eq(flightTest))).thenReturn(flightCreatedTest);

        flightTest = flightService.create(flightTest);

        when(flightRepository.findById(eq(flightTest.getId()))).thenReturn(Optional.of(flightTest));
        doNothing().when(flightRepository).delete(flightTest);

        flightService.delete(flightTest.getId());

        verify(flightRepository, times(1)).save(any());
        verify(flightRepository, times(1)).findById(any());
        verify(flightRepository, times(1)).delete(any());
        verify(flightRepository, times(1)).findSameFlight(any(), any(), any(), any(), any());
    }
}