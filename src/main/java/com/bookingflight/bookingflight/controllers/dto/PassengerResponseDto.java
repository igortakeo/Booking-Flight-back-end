package com.bookingflight.bookingflight.controllers.dto;

import java.util.List;

public class PassengerResponseDto {

    public String cpf;

    public String name;

    public String lastName;

    public Integer age;

    public String email;

    public String telephone;

    public List<BookingWithoutListAndPassengerResponseDto> bookings;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<BookingWithoutListAndPassengerResponseDto> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingWithoutListAndPassengerResponseDto> bookings) {
        this.bookings = bookings;
    }
}
