package com.bookingflight.bookingflight.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "airline", schema = "public")
public class Airline {

    @Id
    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "number_planes")
    private Integer numberPlanes;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "telephone", length = 20)
    private String telephone;

    @ManyToMany(mappedBy = "airlines")
    private List<Airport> airports;

    @OneToMany(mappedBy = "airline")
    private List<Airplane> airplanes;

    public Airline() {
    }

    public Airline(String code, String name, Integer numberPlanes, String email, String telephone) {
        this.code = code;
        this.name = name;
        this.numberPlanes = numberPlanes;
        this.email = email;
        this.telephone = telephone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberPlanes() {
        return numberPlanes;
    }

    public void setNumberPlanes(Integer numberPlanes) {
        this.numberPlanes = numberPlanes;
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

    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

    public List<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(List<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", numberPlanes=" + numberPlanes +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
