package com.bookingflight.bookingflight.domain;

import javax.persistence.*;

@Entity
@Table(name = "airport", schema = "public")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "number")
    private Integer number;

    @Column(name = "cep", nullable = false, length = 20)
    private String cep;

    @Column(name = "city", length = 50)
    private String city;

    public Airport() {
    }

    public Airport(Long id, String name, String street, Integer number, String cep, String city) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.number = number;
        this.cep = cep;
        this.city = city;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Airport airport = (Airport) o;

        return id != null ? id.equals(airport.id) : airport.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", cep='" + cep + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
