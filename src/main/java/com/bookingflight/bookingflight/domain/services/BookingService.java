package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.Booking;
import com.bookingflight.bookingflight.domain.Flight;
import com.bookingflight.bookingflight.domain.Passenger;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotAllowedToBeCreateException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final PassengerService passengerService;

    private final FlightService flightService;

    private final ClassFlightService classFlightService;

    public BookingService(
            BookingRepository bookingRepository,
            PassengerService passengerService,
            FlightService flightService,
            ClassFlightService classFlightService) {

        this.bookingRepository = bookingRepository;
        this.passengerService = passengerService;
        this.flightService = flightService;
        this.classFlightService = classFlightService;
    }

    public List<Booking> findByCpf(String cpf) {
        Optional<List<Booking>> obj = bookingRepository.findByCpf(cpf);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found with CPF = " + cpf));
    }

    public List<Booking> findById(Long id) {
        Optional<List<Booking>> obj = bookingRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found with Id = " + id));
    }

    public Booking create(Booking obj) {

        obj.setPassenger(passengerService.findByCpf(obj.getPassenger().getCpf()));
        obj.setFlight(flightService.findById(obj.getFlight().getId()));
        obj.setClassFlight(classFlightService.findByClassTravelAndFlightId(obj.getClassTravel(), obj.getFlight().getId()));

        Booking bookingVerify = bookingRepository.findByCpfAndId(
                obj.getPassenger().getCpf(),
                obj.getFlight().getId()
        );

        if(bookingVerify != null) {
            throw new ObjectAlreadyExistException("Already exist a booking for this passenger on this flight");
        }

        if(bookingRepository.seatNotAvailable(obj.getSeat(), obj.getClassTravel()) != null){
            throw new ObjectAlreadyExistException("Seat is not available");
        }

        Integer numberOfSeatsOnAirplane = bookingRepository.numberOfSeatsOnAirplane(obj.getFlight().getId());
        Integer numberOfBookings = bookingRepository.numberOfBookings(obj.getFlight().getId());

        if(numberOfBookings >= numberOfSeatsOnAirplane){
            throw new ObjectNotAllowedToBeCreateException("FLight is full");
        }

        return bookingRepository.save(obj);
    }

    public void delete(String cpf, Long id) {
        Booking booking = bookingRepository.findByCpfAndId(cpf, id);
        Passenger passenger = booking.getPassenger();
        Flight flight = booking.getFlight();

        passenger.getBookings().removeIf(
                b -> b.getPassenger().getCpf().equals(cpf) && b.getFlight().getId().equals(id)
        );

        flight.getBookings().removeIf(
                b -> b.getPassenger().getCpf().equals(cpf) && b.getFlight().getId().equals(id)
        );

        bookingRepository.deleteByCpfAndId(cpf, id);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
}
