package com.bookingflight.bookingflight.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "class_flight", schema = "public")
public class ClassFlight {

    @Id
    @Column(name = "class_travel")
    private ClassTravelEnum classTravel;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToMany
    @JoinTable(name = "class_flight_flight",
        joinColumns = { @JoinColumn(name = "class_travel_id", referencedColumnName = "class_travel",
            foreignKey = @ForeignKey(name = "fk_class_flight_flight_class_flight"))},
        inverseJoinColumns = { @JoinColumn(name = "flight_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_class_flight_flight_flight"))})
    private List<Flight>flights;

    public ClassFlight() {
    }

    public ClassFlight(ClassTravelEnum classTravel, BigDecimal price, List<Flight> flights) {
        this.classTravel = classTravel;
        this.price = price;
        this.flights = flights;
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

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return "ClassFlight{" +
                "classTravel=" + classTravel +
                ", price=" + price +
                ", flights=" + flights +
                '}';
    }
}
