package com.bookingflight.bookingflight.controllers.dto;

import com.bookingflight.bookingflight.domain.ClassTravelEnum;

import java.math.BigDecimal;

public class ClassFlightWithoutListResponseDto {

    private ClassTravelEnum classTravel;

    private BigDecimal price;

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
}