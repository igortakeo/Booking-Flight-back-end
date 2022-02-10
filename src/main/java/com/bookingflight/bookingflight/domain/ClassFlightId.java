package com.bookingflight.bookingflight.domain;

import java.io.Serializable;

public class ClassFlightId implements Serializable {

    private ClassTravelEnum classTravel;

    private Long flight;

    public ClassFlightId() {
    }

    public ClassFlightId(ClassTravelEnum classTravel, Long flight) {
        this.classTravel = classTravel;
        this.flight = flight;
    }

    public ClassTravelEnum getClassTravel() {
        return classTravel;
    }

    public void setClassTravel(ClassTravelEnum classTravel) {
        this.classTravel = classTravel;
    }

    public Long getFlight() {
        return flight;
    }

    public void setFlight(Long flights) {
        this.flight = flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassFlightId that = (ClassFlightId) o;

        if (classTravel != that.classTravel) return false;
        return flight != null ? flight.equals(that.flight) : that.flight == null;
    }

    @Override
    public int hashCode() {
        int result = classTravel != null ? classTravel.hashCode() : 0;
        result = 31 * result + (flight != null ? flight.hashCode() : 0);
        return result;
    }
}
