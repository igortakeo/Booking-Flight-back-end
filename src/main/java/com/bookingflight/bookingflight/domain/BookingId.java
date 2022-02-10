package com.bookingflight.bookingflight.domain;

import java.io.Serializable;

public class BookingId  implements Serializable {

    private String passenger;

    private Long flight;

    public BookingId() {
    }

    public BookingId(String passenger, Long flight) {
        this.passenger = passenger;
        this.flight = flight;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public Long getFlight() {
        return flight;
    }

    public void setFlight(Long flight) {
        this.flight = flight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookingId bookingId = (BookingId) o;

        if (passenger != null ? !passenger.equals(bookingId.passenger) : bookingId.passenger != null) return false;
        return flight != null ? flight.equals(bookingId.flight) : bookingId.flight == null;
    }

    @Override
    public int hashCode() {
        int result = passenger != null ? passenger.hashCode() : 0;
        result = 31 * result + (flight != null ? flight.hashCode() : 0);
        return result;
    }
}
