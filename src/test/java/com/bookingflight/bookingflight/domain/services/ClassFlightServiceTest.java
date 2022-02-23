package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.*;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectWithIncorretInformationsException;
import com.bookingflight.bookingflight.repositories.ClassFlightRepository;
import com.bookingflight.bookingflight.repositories.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ClassFlightServiceTest {

    @InjectMocks
    ClassFlightService classFlightService;

    @InjectMocks
    FlightService flightService;

    @Mock
    ClassFlightRepository classFlightRepository;

    @Mock
    FlightRepository flightRepository;

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.initMocks(this);

        this.classFlightService = new ClassFlightService(
                classFlightRepository,
                this.flightService
        );
    }

    @Test
    void findByClassTravelAndFlightId() {
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

        final Flight flightTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        ClassFlight classFlightTest = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("520.00"),
                flightTest
        );

        when(flightRepository.findById(eq(flightTest.getId()))).thenReturn(Optional.of(flightTest));
        when(classFlightRepository.save(eq(classFlightTest))).thenReturn(classFlightTest);

        classFlightTest = classFlightService.create(classFlightTest);

        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest.getClassTravel()),
                eq(classFlightTest.getFlight().getId())
        )).thenReturn(Optional.of(classFlightTest));

        ClassFlight classFlightReturnTest = classFlightService.findByClassTravelAndFlightId(
                classFlightTest.getClassTravel(),
                classFlightTest.getFlight().getId()
        );

        assertEquals(classFlightTest.getClassTravel(), classFlightReturnTest.getClassTravel());
        assertEquals(classFlightTest.getPrice(), classFlightReturnTest.getPrice());
        assertEquals(classFlightTest.getFlight(), classFlightReturnTest.getFlight());
        verify(flightRepository, times(1)).findById(any());
        verify(classFlightRepository, times(1)).save(any());
        verify(classFlightRepository, times(2)).findByClassTravelAndFlightId(any(), any());
    }

    @Test
    void findByClassTravelAndFlightIdClassFLightNotFound(){
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

        final Flight flightTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

       when(classFlightRepository.findByClassTravelAndFlightId(
               eq(ClassTravelEnum.FIRST_CLASS),
               eq(flightTest.getId())
       )).thenThrow(ObjectNotFoundException.class);

       assertThrows(ObjectNotFoundException.class, () -> classFlightService.findByClassTravelAndFlightId(
               ClassTravelEnum.FIRST_CLASS,
               flightTest.getId()
       ));

       verify(classFlightRepository, times(1)).findByClassTravelAndFlightId(any(), any());
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
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusDays(5)
        );

        final ClassFlight classFlightTest1 = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("520.00"),
                flightTest1
        );

        final ClassFlight classFlightTest2 = new ClassFlight(
                ClassTravelEnum.BUSINESS,
                new BigDecimal("420.00"),
                flightTest1
        );

        final ClassFlight classFlightTest3 = new ClassFlight(
                ClassTravelEnum.ECONOMY,
                new BigDecimal("120.00"),
                flightTest2
        );

        List<ClassFlight> classFlightTestList = new ArrayList<>(Arrays.asList(
                classFlightTest1,
                classFlightTest2,
                classFlightTest3
        ));

        when(classFlightRepository.findAll()).thenReturn(classFlightTestList);

        List<ClassFlight> classFlightReturnList = classFlightRepository.findAll();

        assertEquals(classFlightTestList.size(), classFlightReturnList.size());

        for(int i=0; i<classFlightReturnList.size(); i++){
            assertEquals(classFlightTestList.get(i), classFlightReturnList.get(i));
        }

        verify(classFlightRepository, times(1)).findAll();
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

        final Flight flightTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final ClassFlight classFlightTest1 = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("520.00"),
                flightTest
        );

        final ClassFlight classFlightTest2 = new ClassFlight(
                ClassTravelEnum.BUSINESS,
                new BigDecimal("420.00"),
                flightTest
        );

        List<ClassFlight> classFlightTestList = new ArrayList<>(Arrays.asList(
                classFlightTest1,
                classFlightTest2
        ));

        when(classFlightRepository.findById(eq(flightTest.getId()))).thenReturn(Optional.of(classFlightTestList));

        List<ClassFlight> classFlightReturnList = classFlightService.findById(flightTest.getId());

        assertEquals(classFlightTestList.size(), classFlightReturnList.size());

        for(int i=0; i<classFlightReturnList.size(); i++){
            assertEquals(classFlightTestList.get(i), classFlightReturnList.get(i));
        }

        verify(classFlightRepository, times(1)).findById((Long) any());
    }

    @Test
    void findByIdClassFlightNotFound(){
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

        final Flight flightTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        when(classFlightRepository.findById(eq(flightTest.getId()))).thenThrow(ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class, () -> classFlightService.findById(flightTest.getId()));

        verify(classFlightRepository, times(1)).findById((Long) any());
    }

    @Test
    void createClassFlightAlreadyExist() {
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

        final Flight flightTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        ClassFlight classFlightTest1 = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("520.00"),
                flightTest
        );

        ClassFlight classFlight2 = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("520.00"),
                flightTest
        );

        when(flightRepository.findById(eq(flightTest.getId()))).thenReturn(Optional.of(flightTest));
        when(classFlightRepository.save(eq(classFlightTest1))).thenReturn(classFlightTest1);

        classFlightTest1 = classFlightService.create(classFlightTest1);

        when(flightRepository.findById(eq(flightTest.getId()))).thenReturn(Optional.of(flightTest));
        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlight2.getClassTravel()),
                eq(classFlight2.getFlight().getId())
        )).thenReturn(Optional.ofNullable(classFlightTest1));

        assertThrows(ObjectAlreadyExistException.class, () -> classFlightService.create(classFlight2));

        verify(flightRepository, times(2)).findById(any());
        verify(classFlightRepository, times(2)).findByClassTravelAndFlightId(any(), any());
        verify(classFlightRepository, times(1)).save(any());
    }

    @Test
    void update() {
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

        final Flight flightTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final ClassFlight classFlightTest = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("520.00"),
                flightTest
        );

        final ClassFlight classFlightUpdateTest = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("620"),
                flightTest
        );

        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest.getClassTravel()),
                eq(classFlightTest.getFlight().getId()
        ))).thenReturn(Optional.of(classFlightTest));
        when(classFlightRepository.save(eq(classFlightUpdateTest))).thenReturn(classFlightUpdateTest);

        classFlightService.update(
                classFlightTest.getFlight().getId(),
                classFlightTest.getClassTravel(),
                classFlightUpdateTest
        );

        assertEquals(classFlightTest.getClassTravel(), classFlightUpdateTest.getClassTravel());
        assertEquals(classFlightTest.getPrice(), classFlightUpdateTest.getPrice());
        assertEquals(classFlightTest.getFlight(), classFlightUpdateTest.getFlight());

        verify(classFlightRepository, times(1)).findByClassTravelAndFlightId(any(), any());
        verify(classFlightRepository, times(1)).save(any());
    }

    @Test
    void updateIncorrectInformations(){
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

        final Flight flightTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        final ClassFlight classFlightTest = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("520.00"),
                flightTest
        );

        final ClassFlight classFlightUpdateTest = new ClassFlight(
                ClassTravelEnum.ECONOMY,
                new BigDecimal("620"),
                flightTest
        );

        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest.getClassTravel()),
                eq(classFlightTest.getFlight().getId()
                ))).thenReturn(Optional.of(classFlightTest));
        when(classFlightRepository.save(eq(classFlightUpdateTest))).thenReturn(classFlightUpdateTest);

        assertThrows(ObjectWithIncorretInformationsException.class, () -> classFlightService.update(
                classFlightTest.getFlight().getId(),
                classFlightTest.getClassTravel(),
                classFlightUpdateTest
        ));

        verify(classFlightRepository, times(1)).findByClassTravelAndFlightId(any(), any());
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

        final Flight flightTest = new Flight(
                1L,
                airportTest,
                airplaneTest,
                "Sao Paulo",
                "Rio de Janeiro",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
        );

        ClassFlight classFlightTest = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("520.00"),
                flightTest
        );

        when(flightRepository.findById(eq(flightTest.getId()))).thenReturn(Optional.of(flightTest));
        when(classFlightRepository.save(eq(classFlightTest))).thenReturn(classFlightTest);

        classFlightTest = classFlightService.create(classFlightTest);

        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest.getClassTravel()),
                eq(classFlightTest.getFlight().getId())
        )).thenReturn(Optional.of(classFlightTest));
        when(flightRepository.findById(eq(flightTest.getId()))).thenReturn(Optional.of(flightTest));
        doNothing().when(classFlightRepository).delete(classFlightTest);

        classFlightService.delete(classFlightTest.getFlight().getId(), classFlightTest.getClassTravel());

        verify(flightRepository, times(2)).findById(any());
        verify(classFlightRepository, times(2)).findByClassTravelAndFlightId(any(), any());
        verify(classFlightRepository, times(1)).save(any());
        verify(classFlightRepository, times(1)).delete(any());

    }
}