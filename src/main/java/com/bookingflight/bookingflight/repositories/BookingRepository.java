package com.bookingflight.bookingflight.repositories;

import com.bookingflight.bookingflight.domain.Booking;
import com.bookingflight.bookingflight.domain.BookingId;
import com.bookingflight.bookingflight.domain.ClassTravelEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, BookingId> {

    @Query("select b, cf " +
            "from Booking b " +
            "left join ClassFlight cf on cf.classTravel = b.classTravel and cf.flight.id = b.flight.id " +
            "where b.passenger.cpf = :cpf")
    Optional<List<Booking>> findByCpf(String cpf);

    @Query("select b, cf " +
            "from Booking b " +
            "left join ClassFlight cf on cf.classTravel = b.classTravel and cf.flight.id = b.flight.id " +
            "where b.flight.id = :id")
    Optional<List<Booking>> findById(Long id);

    @Query("select b, cf " +
            "from Booking b " +
            "left join ClassFlight cf on cf.classTravel = b.classTravel and cf.flight.id = b.flight.id " +
            "where b.flight.id = :id and b.passenger.cpf = :cpf")
    Booking findByCpfAndId(String cpf, Long id);

    @Query("select b " +
            "from Booking b " +
            "where b.seat = :seat and b.classTravel = :classTravel")
    Booking seatNotAvailable(Integer seat, ClassTravelEnum classTravel);

    @Query("select a.maximumNumberPassengers  "+
            "from Flight f " +
            "left join Airplane a on a.id = f.airplane.id " +
            "where f.id = :id")
    Integer numberOfSeatsOnAirplane(Long id);

    @Query("select count (b)" +
            "from Booking b " +
            "where b.flight.id = :id ")
    Integer numberOfBookings(Long id);

    @Transactional
    @Modifying
    @Query("delete from Booking b " +
            "where b.passenger.cpf = :cpf and b.flight.id = :id")
    void deleteByCpfAndId(String cpf, Long id);
}
