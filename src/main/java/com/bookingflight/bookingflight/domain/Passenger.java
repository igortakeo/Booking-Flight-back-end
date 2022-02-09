package com.bookingflight.bookingflight.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "passenger", schema = "public")
public class Passenger {

    @Id
    public String cpf;

    @Column(name = "name", nullable = false, length = 50)
    public String name;

    @Column(name = "last_name", nullable = false, length = 50)
    public String lastName;

    @Column(name = "age")
    public Integer age;

    @Column(name = "email", length = 30)
    public String email;

    @Column(name = "telephone", length = 20)
    public String telephone;

    public Passenger() {
    }

    public Passenger(String cpf, String name, String lastName, Integer age, String email, String telephone) {
        this.cpf = cpf;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.telephone = telephone;
    }

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

    @Override
    public String toString() {
        return "Passenger{" +
                "cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
