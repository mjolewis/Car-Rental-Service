package com.crd.carrental.controllers;

import com.crd.carrental.rentalportfolio.CarTypes;
import com.crd.carrental.rentalportfolio.StoreLocations;

/**********************************************************************************************************************
 * Data model representing the users request to rent a car.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ReservationRequest {
    private StoreLocations location;
    private CarTypes carType;
    private String reservationStartDateAndTime;
    private String reservationEndDateAndTime;
    private String fName;
    private String lName;
    private String email;
    private long creditCard;

    public ReservationRequest() {
    }

    public ReservationRequest(StoreLocations location, CarTypes carType, String reservationStartDateAndTime,
                              String reservationEndDateAndTime, String fName, String lName, String email, long creditCard) {

        this.location = location;
        this.carType = carType;
        this.reservationStartDateAndTime = reservationStartDateAndTime;
        this.reservationEndDateAndTime = reservationEndDateAndTime;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.creditCard = creditCard;
    }

    public StoreLocations getLocation() { return location; }

    public void setLocation(StoreLocations location) {
        this.location = location;
    }

    public CarTypes getCarType() {
        return carType;
    }

    public void setCarType(CarTypes carType) { this.carType = carType; }

    public String getReservationStartDateAndTime() {
        return reservationStartDateAndTime;
    }

    public void setReservationStartDateAndTime(String reservationStartDateAndTime) {
        this.reservationStartDateAndTime = reservationStartDateAndTime;
    }

    public String getReservationEndDateAndTime() {
        return reservationEndDateAndTime;
    }

    public void setReservationEndDateAndTime(String reservationEndDateAndTime) {
        this.reservationEndDateAndTime = reservationEndDateAndTime;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(long creditCard) {
        this.creditCard = creditCard;
    }
}
