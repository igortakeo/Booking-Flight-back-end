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

    @Column(name = "class_travel", nullable = false)
    private ClassTravelEnum classTravel;

    @Column(name = "seat", nullable = false)
    private Integer seat;

    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "flight_id", referencedColumnName = "flight_id", insertable = false, updatable = false),
            @JoinColumn(name = "class_travel", referencedColumnName = "class_travel", insertable = false, updatable = false)
    }, foreignKey = @ForeignKey(name = "fk_booking_class_flight"))
    private ClassFlight classFlight;

    public Booking() {
    }

    public Booking(Passenger passenger, Flight flight, ClassTravelEnum classTravel, Integer seat, ClassFlight classFlight) {
        this.passenger = passenger;
        this.flight = flight;
        this.classTravel = classTravel;
        this.seat = seat;
        this.classFlight = classFlight;
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

    public ClassTravelEnum getClassTravel() {
        return classTravel;
    }

    public void setClassTravel(ClassTravelEnum classTravel) {
        this.classTravel = classTravel;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public ClassFlight getClassFlight() {
        return classFlight;
    }

    public void setClassFlight(ClassFlight classFlight) {
        this.classFlight = classFlight;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "classTravel=" + classTravel +
                ", seat=" + seat +
                '}';
    }
}
