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
    private String firstName;
    private String lastName;
    private String emailAddress;
    private long creditCardNumber;

    public ReservationRequest() {
    }

    public ReservationRequest(StoreLocations location, CarTypes carType, String reservationStartDateAndTime,
                              String reservationEndDateAndTime, String firstName, String lastName,
                              String emailAddress, long creditCardNumber) {

        this.location = location;
        this.carType = carType;
        this.reservationStartDateAndTime = reservationStartDateAndTime;
        this.reservationEndDateAndTime = reservationEndDateAndTime;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.creditCardNumber = creditCardNumber;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
