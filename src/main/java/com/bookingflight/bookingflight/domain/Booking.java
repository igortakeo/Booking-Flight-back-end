package com.bookingflight.bookingflight.domain;

import javax.persistence.*;

@Entity
@Table(name = "booking", schema = "public")
@IdClass(BookingId.class)
public class Booking {

    @Id
    @ManyToOne
    @JoinColumn(name = "passenger_cpf", nullable = false,
        foreignKey = @ForeignKey(name = "fk_booking_passenger"))
    private Passenger passenger;

    @Id
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_booking_flight"))
    private Flight flight;

    @Column(name = "class_travel", nullable = false, length = 20)
    private ClassTravelEnum classTravel;

    @Column(name = "seat", nullable = false)
    private Integer seat;

    public Booking() {
    }

    public Booking(Passenger passenger, Flight flight, ClassTravelEnum classFlight) {
        this.passenger = passenger;
        this.flight = flight;
        this.classTravel = classFlight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public ClassTravelEnum getClassFlight() {
        return classTravel;
    }

    public void setClassFlight(ClassTravelEnum classFlight) {
        this.classTravel = classFlight;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "passenger=" + passenger +
                ", flight=" + flight +
                ", classTravel=" + classTravel +
                ", seat=" + seat +
                '}';
    }
}
