package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.*;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotAllowedToBeCreateException;
import com.bookingflight.bookingflight.repositories.BookingRepository;
import com.bookingflight.bookingflight.repositories.ClassFlightRepository;
import com.bookingflight.bookingflight.repositories.FlightRepository;
import com.bookingflight.bookingflight.repositories.PassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
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
class BookingServiceTest {

    @InjectMocks
    BookingService bookingService;

    @InjectMocks
    PassengerService passengerService;

    @InjectMocks
    FlightService flightService;

    @InjectMocks
    ClassFlightService classFlightService;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    PassengerRepository passengerRepository;

    @Mock
    FlightRepository flightRepository;

    @Mock
    ClassFlightRepository classFlightRepository;

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.initMocks(this);

        this.bookingService = new BookingService(
                bookingRepository,
                this.passengerService,
                this.flightService,
                this.classFlightService
        );
    }

    @Test
    void findByCpf() {
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
        final Passenger passengerTest = new Passenger(
                "cpfTest",
                "nameTest",
                "lastNameTest",
                22,
                "emailTest",
                "telephoneTest"
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
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("420.00"),
                flightTest2
        );

        Booking bookingTest1 = new Booking(
                passengerTest,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                30,
                classFlightTest1
        );

        Booking bookingTest2 = new Booking(
                passengerTest,
                flightTest2,
                ClassTravelEnum.FIRST_CLASS,
                50,
                classFlightTest2
        );

        when(passengerRepository.findByCpf(eq(passengerTest.getCpf()))).thenReturn(Optional.of(passengerTest));
        when(flightRepository.findById(eq(flightTest1.getId()))).thenReturn(Optional.of(flightTest1));
        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest1.getClassTravel()),
                eq(flightTest1.getId())
        )).thenReturn(Optional.of(classFlightTest1));
        when(bookingRepository.numberOfSeatsOnAirplane(eq(flightTest1.getId()))).thenReturn(52);
        when(bookingRepository.numberOfBookings(eq(flightTest1.getId()))).thenReturn(0);
        when(bookingRepository.save(eq(bookingTest1))).thenReturn(bookingTest1);

        bookingTest1 = bookingService.create(bookingTest1);

        when(passengerRepository.findByCpf(eq(passengerTest.getCpf()))).thenReturn(Optional.of(passengerTest));
        when(flightRepository.findById(eq(flightTest2.getId()))).thenReturn(Optional.of(flightTest2));
        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest2.getClassTravel()),
                eq(flightTest2.getId())
        )).thenReturn(Optional.of(classFlightTest1));
        when(bookingRepository.numberOfSeatsOnAirplane(eq(flightTest2.getId()))).thenReturn(52);
        when(bookingRepository.numberOfBookings(eq(flightTest2.getId()))).thenReturn(0);
        when(bookingRepository.save(eq(bookingTest2))).thenReturn(bookingTest2);

        bookingTest2 = bookingService.create(bookingTest2);

        List<Booking> bookingTestList = new ArrayList<>(Arrays.asList(
                bookingTest1,
                bookingTest2
        ));

        when(bookingRepository.findByCpf(eq(passengerTest.getCpf()))).thenReturn(Optional.of(bookingTestList));

        List<Booking> bookingReturnList = bookingService.findByCpf(passengerTest.getCpf());

        assertEquals(bookingTestList.size(), bookingReturnList.size());

        for(int i=0; i<bookingReturnList.size(); i++){
            assertEquals(bookingTestList.get(i), bookingReturnList.get(i));
        }

        verify(bookingRepository, times(2)).save(any());
        verify(bookingRepository, times(2)).numberOfBookings(any());
        verify(bookingRepository, times(2)).numberOfSeatsOnAirplane(any());
        verify(bookingRepository, times(2)).seatNotAvailable(any(), any());
        verify(bookingRepository, times(2)).findByCpfAndId(any(), any());
        verify(bookingRepository, times(1)).findByCpf(any());
        verify(passengerRepository, times(2)).findByCpf(any());
        verify(flightRepository, times(2)).findById(any());
        verify(classFlightRepository, times(2)).findByClassTravelAndFlightId(any(), any());
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

        final Flight flightTest1 = new Flight(
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
                flightTest1
        );

        Booking bookingTest1 = new Booking(
                passengerTest1,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                30,
                classFlightTest1
        );

        Booking bookingTest2 = new Booking(
                passengerTest2,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                50,
                classFlightTest1
        );

        List<Booking> bookingTestList = new ArrayList<>(Arrays.asList(
                bookingTest1,
                bookingTest2
        ));

        when(bookingRepository.findById(eq(flightTest1.getId()))).thenReturn(Optional.of(bookingTestList));

        List<Booking> bookingReturnList = bookingService.findById(flightTest1.getId());

        assertEquals(bookingTestList.size(), bookingReturnList.size());

        for(int i=0; i<bookingReturnList.size(); i++){
            assertEquals(bookingTestList.get(i), bookingReturnList.get(i));
        }

        verify(bookingRepository, times(1)).findById((Long) any());
    }

    @Test
    void createBookingAlreadyExistInThisFLight() {
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

        final Passenger passengerTest1 = new Passenger(
                "cpfTest1",
                "nameTest1",
                "lastNameTest1",
                22,
                "emailTest",
                "telephoneTest"
        );

        final Flight flightTest1 = new Flight(
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
                flightTest1
        );

        Booking bookingTest1 = new Booking(
                passengerTest1,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                30,
                classFlightTest1
        );

        Booking bookingTest2 = new Booking(
                passengerTest1,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                31,
                classFlightTest1
        );

        when(passengerRepository.findByCpf(eq(passengerTest1.getCpf()))).thenReturn(Optional.of(passengerTest1));
        when(flightRepository.findById(eq(flightTest1.getId()))).thenReturn(Optional.of(flightTest1));
        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest1.getClassTravel()),
                eq(flightTest1.getId())
        )).thenReturn(Optional.of(classFlightTest1));
        when(bookingRepository.numberOfSeatsOnAirplane(eq(flightTest1.getId()))).thenReturn(52);
        when(bookingRepository.numberOfBookings(eq(flightTest1.getId()))).thenReturn(0);
        when(bookingRepository.save(eq(bookingTest1))).thenReturn(bookingTest1);

        bookingTest1 = bookingService.create(bookingTest1);

        when(passengerRepository.findByCpf(eq(passengerTest1.getCpf()))).thenReturn(Optional.of(passengerTest1));
        when(flightRepository.findById(eq(flightTest1.getId()))).thenReturn(Optional.of(flightTest1));
        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest1.getClassTravel()),
                eq(flightTest1.getId())
        )).thenReturn(Optional.of(classFlightTest1));
        when(bookingRepository.findByCpfAndId(
                eq(passengerTest1.getCpf()),
                eq(flightTest1.getId())
        )).thenReturn(bookingTest1);

        assertThrows(ObjectAlreadyExistException.class, () -> bookingService.create(bookingTest2));

        verify(bookingRepository, times(1)).save(any());
        verify(bookingRepository, times(1)).numberOfBookings(any());
        verify(bookingRepository, times(1)).numberOfSeatsOnAirplane(any());
        verify(bookingRepository, times(1)).seatNotAvailable(any(), any());
        verify(bookingRepository, times(2)).findByCpfAndId(any(), any());
        verify(passengerRepository, times(2)).findByCpf(any());
        verify(flightRepository, times(2)).findById(any());
        verify(classFlightRepository, times(2)).findByClassTravelAndFlightId(any(), any());
    }

    @Test
    void createSeatIsNotAvailable(){
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

        final Flight flightTest1 = new Flight(
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
                flightTest1
        );

        Booking bookingTest1 = new Booking(
                passengerTest1,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                30,
                classFlightTest1
        );

        Booking bookingTest2 = new Booking(
                passengerTest2,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                30,
                classFlightTest1
        );

        when(passengerRepository.findByCpf(eq(passengerTest1.getCpf()))).thenReturn(Optional.of(passengerTest1));
        when(flightRepository.findById(eq(flightTest1.getId()))).thenReturn(Optional.of(flightTest1));
        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest1.getClassTravel()),
                eq(flightTest1.getId())
        )).thenReturn(Optional.of(classFlightTest1));
        when(bookingRepository.numberOfSeatsOnAirplane(eq(flightTest1.getId()))).thenReturn(52);
        when(bookingRepository.numberOfBookings(eq(flightTest1.getId()))).thenReturn(0);
        when(bookingRepository.save(eq(bookingTest1))).thenReturn(bookingTest1);

        bookingTest1 = bookingService.create(bookingTest1);

        when(passengerRepository.findByCpf(eq(passengerTest2.getCpf()))).thenReturn(Optional.of(passengerTest2));
        when(flightRepository.findById(eq(flightTest1.getId()))).thenReturn(Optional.of(flightTest1));
        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest1.getClassTravel()),
                eq(flightTest1.getId())
        )).thenReturn(Optional.of(classFlightTest1));
        when(bookingRepository.seatNotAvailable(
                eq(bookingTest2.getSeat()),
                eq(bookingTest2.getClassTravel())
        )).thenReturn(bookingTest1);

        assertThrows(ObjectAlreadyExistException.class, () -> bookingService.create(bookingTest2));

        verify(bookingRepository, times(1)).save(any());
        verify(bookingRepository, times(1)).numberOfBookings(any());
        verify(bookingRepository, times(1)).numberOfSeatsOnAirplane(any());
        verify(bookingRepository, times(2)).seatNotAvailable(any(), any());
        verify(bookingRepository, times(2)).findByCpfAndId(any(), any());
        verify(passengerRepository, times(2)).findByCpf(any());
        verify(flightRepository, times(2)).findById(any());
        verify(classFlightRepository, times(2)).findByClassTravelAndFlightId(any(), any());
    }

    @Test
    void createFlightIsFull(){
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
                1
        );

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

        final Flight flightTest1 = new Flight(
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
                flightTest1
        );

        Booking bookingTest1 = new Booking(
                passengerTest1,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                30,
                classFlightTest1
        );

        Booking bookingTest2 = new Booking(
                passengerTest2,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                30,
                classFlightTest1
        );

        when(passengerRepository.findByCpf(eq(passengerTest1.getCpf()))).thenReturn(Optional.of(passengerTest1));
        when(flightRepository.findById(eq(flightTest1.getId()))).thenReturn(Optional.of(flightTest1));
        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest1.getClassTravel()),
                eq(flightTest1.getId())
        )).thenReturn(Optional.of(classFlightTest1));
        when(bookingRepository.numberOfSeatsOnAirplane(eq(flightTest1.getId()))).thenReturn(52);
        when(bookingRepository.numberOfBookings(eq(flightTest1.getId()))).thenReturn(0);
        when(bookingRepository.save(eq(bookingTest1))).thenReturn(bookingTest1);

        bookingTest1 = bookingService.create(bookingTest1);

        when(passengerRepository.findByCpf(eq(passengerTest2.getCpf()))).thenReturn(Optional.of(passengerTest2));
        when(flightRepository.findById(eq(flightTest1.getId()))).thenReturn(Optional.of(flightTest1));
        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest1.getClassTravel()),
                eq(flightTest1.getId())
        )).thenReturn(Optional.of(classFlightTest1));
        when(bookingRepository.numberOfSeatsOnAirplane(eq(flightTest1.getId()))).thenReturn(1);
        when(bookingRepository.numberOfBookings(eq(flightTest1.getId()))).thenReturn(1);

        assertThrows(ObjectNotAllowedToBeCreateException.class, () -> bookingService.create(bookingTest2));

        verify(bookingRepository, times(1)).save(any());
        verify(bookingRepository, times(2)).numberOfBookings(any());
        verify(bookingRepository, times(2)).numberOfSeatsOnAirplane(any());
        verify(bookingRepository, times(2)).seatNotAvailable(any(), any());
        verify(bookingRepository, times(2)).findByCpfAndId(any(), any());
        verify(passengerRepository, times(2)).findByCpf(any());
        verify(flightRepository, times(2)).findById(any());
        verify(classFlightRepository, times(2)).findByClassTravelAndFlightId(any(), any());
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
                1
        );

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

        final Flight flightTest1 = new Flight(
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
                flightTest1
        );

        Booking bookingTest1 = new Booking(
                passengerTest1,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                30,
                classFlightTest1
        );

        when(passengerRepository.findByCpf(eq(passengerTest1.getCpf()))).thenReturn(Optional.of(passengerTest1));
        when(flightRepository.findById(eq(flightTest1.getId()))).thenReturn(Optional.of(flightTest1));
        when(classFlightRepository.findByClassTravelAndFlightId(
                eq(classFlightTest1.getClassTravel()),
                eq(flightTest1.getId())
        )).thenReturn(Optional.of(classFlightTest1));
        when(bookingRepository.numberOfSeatsOnAirplane(eq(flightTest1.getId()))).thenReturn(52);
        when(bookingRepository.numberOfBookings(eq(flightTest1.getId()))).thenReturn(0);
        when(bookingRepository.save(eq(bookingTest1))).thenReturn(bookingTest1);

        bookingTest1 = bookingService.create(bookingTest1);

        when(bookingRepository.findByCpfAndId(
                eq(bookingTest1.getPassenger().getCpf()),
                eq(bookingTest1.getFlight().getId())
        )).thenReturn(bookingTest1);
        doNothing().when(bookingRepository).deleteByCpfAndId(
                eq(bookingTest1.getPassenger().getCpf()),
                eq(bookingTest1.getFlight().getId())
        );

        bookingService.delete(
                bookingTest1.getPassenger().getCpf(),
                bookingTest1.getFlight().getId()
        );

        verify(bookingRepository, times(1)).save(any());
        verify(bookingRepository, times(1)).numberOfBookings(any());
        verify(bookingRepository, times(1)).numberOfSeatsOnAirplane(any());
        verify(bookingRepository, times(1)).seatNotAvailable(any(), any());
        verify(bookingRepository, times(2)).findByCpfAndId(any(), any());
        verify(bookingRepository, times(1)).deleteByCpfAndId(any(), any());
        verify(passengerRepository, times(1)).findByCpf(any());
        verify(flightRepository, times(1)).findById(any());
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
                "Curitiba",
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusDays(5)
        );

        final ClassFlight classFlightTest1 = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("520.00"),
                flightTest1
        );

        final ClassFlight classFlightTest2 = new ClassFlight(
                ClassTravelEnum.FIRST_CLASS,
                new BigDecimal("520.00"),
                flightTest2
        );

        Booking bookingTest1 = new Booking(
                passengerTest1,
                flightTest1,
                ClassTravelEnum.FIRST_CLASS,
                30,
                classFlightTest1
        );

        Booking bookingTest2 = new Booking(
                passengerTest2,
                flightTest2,
                ClassTravelEnum.FIRST_CLASS,
                31,
                classFlightTest2
        );

        List<Booking> bookingTestList = new ArrayList<>(Arrays.asList(
                bookingTest1,
                bookingTest2
        ));

        when(bookingRepository.findAll()).thenReturn(bookingTestList);

        List<Booking> bookingReturnList = bookingService.findAll();

        assertEquals(bookingTestList.size(), bookingReturnList.size());

        for(int i=0; i<bookingReturnList.size(); i++){
            assertEquals(bookingTestList.get(i), bookingReturnList.get(i));
        }

        verify(bookingRepository, times(1)).findAll();
    }
}