package com.bookingflight.bookingflight.domain;

import javax.persistence.*;

@Entity
@Table(name = "airplane", schema = "public")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "maximum_number_passengers", nullable = false)
    private Integer maximumNumberPassengers;

    @ManyToOne
    @JoinColumn(name = "airline_code", nullable = false,
        foreignKey = @ForeignKey(name = "fk_airplane_airline"))
    private Airline airline;

    public Airplane() {
    }

    public Airplane(Long id, String name, Integer maximumNumberPassengers) {
        this.id = id;
        this.name = name;
        this.maximumNumberPassengers = maximumNumberPassengers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaximumNumberPassengers() {
        return maximumNumberPassengers;
    }

    public void setMaximumNumberPassengers(Integer maximumNumberPassengers) {
        this.maximumNumberPassengers = maximumNumberPassengers;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maximumNumberPassengers=" + maximumNumberPassengers +
                ", airline=" + airline +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airplane airplane = (Airplane) o;

        return id != null ? id.equals(airplane.id) : airplane.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
