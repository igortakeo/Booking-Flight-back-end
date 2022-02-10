package com.bookingflight.bookingflight.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flight", schema = "public")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "airport_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_flight_airport"))
    private Airport airport;

    @ManyToOne
    @JoinColumn(name = "airplane_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_flight_airplane"))
    private Airplane airplane;

    @Column(name = "source", nullable = false, length = 50)
    private String source;

    @Column(name = "target", nullable = false, length = 50)
    private String target;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @OneToMany(mappedBy = "flight")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "flight")
    private List<ClassFlight> classFlights;

    public Flight() {
    }

    public Flight(Long id, String source, String target, LocalDateTime date) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<ClassFlight> getClassFlights() {
        return classFlights;
    }

    public void setClassFlights(List<ClassFlight> classFlights) {
        this.classFlights = classFlights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        return id != null ? id.equals(flight.id) : flight.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", airport=" + airport +
                ", airplane=" + airplane +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", date=" + date +
                ", bookings=" + bookings +
                ", classFlights=" + classFlights +
                '}';
    }
}
