package com.bookingflight.bookingflight.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "class_flight", schema = "public")
@IdClass(ClassFlightId.class)
public class ClassFlight {

    @Id
    @Column(name = "class_travel")
    private ClassTravelEnum classTravel;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Id
    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_class_flight_flight"))
    private Flight flight;

    @OneToOne(mappedBy = "classFlight")
    private Booking booking;

    public ClassFlight() {
    }

    public ClassFlight(ClassTravelEnum classTravel, BigDecimal price, Flight flight) {
        this.classTravel = classTravel;
        this.price = price;
        this.flight = flight;
    }

    public ClassTravelEnum getClassTravel() {
        return classTravel;
    }

    public void setClassTravel(ClassTravelEnum classTravel) {
        this.classTravel = classTravel;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flights) {
        this.flight = flights;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "ClassFlight{" +
                "classTravel=" + classTravel +
                ", price=" + price +
                ", flights=" + flight +
                ", booking=" + booking +
                '}';
    }
}
