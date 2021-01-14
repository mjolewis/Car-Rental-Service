package com.crd.carrental.controllers;

import com.crd.carrental.rentalportfolio.CarTypes;
import com.crd.carrental.rentalportfolio.StoreLocations;

import java.sql.Timestamp;

/**********************************************************************************************************************
 * Data model representing the users request to rent a car.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ReservationRequest {
    private StoreLocations location;
    private CarTypes carType;
    private Timestamp reservationStartDateAndTime;
    private Timestamp reservationEndDateAndTime;

    public ReservationRequest() {
    }

    public ReservationRequest(StoreLocations location, CarTypes carType, Timestamp reservationStartDateAndTime,
                              Timestamp reservationEndDateAndTime) {

        this.location = location;
        this.carType = carType;
        this.reservationStartDateAndTime = reservationStartDateAndTime;
        this.reservationEndDateAndTime = reservationEndDateAndTime;
    }

    public StoreLocations getLocation() { return location; }

    public void setLocation(StoreLocations location) {
        this.location = location;
    }

    public CarTypes getCarType() {
        return carType;
    }

    public void setCarType(CarTypes carType) { this.carType = carType; }

    public Timestamp getReservationStartDateAndTime() {
        return reservationStartDateAndTime;
    }

    public void setReservationStartDateAndTime(Timestamp reservationStartDateAndTime) {
        this.reservationStartDateAndTime = reservationStartDateAndTime;
    }

    public Timestamp getReservationEndDateAndTime() {
        return reservationEndDateAndTime;
    }

    public void setReservationEndDateAndTime(Timestamp reservationEndDateAndTime) {
        this.reservationEndDateAndTime = reservationEndDateAndTime;
    }
}
