package com.crd.carrental.controllers;

/**********************************************************************************************************************
 * Data model representing the users request to rent a car.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ReservationRequest {
    private String location;
    private String carType;
    private String reservationStartDateAndTime;
    private String reservationEndDateAndTime;

    public ReservationRequest() {
    }

    public ReservationRequest(String location, String carType, String reservationStartDateAndTime,
                              String reservationEndDateAndTime) {
        this.location = location;
        this.carType = carType;
        this.reservationStartDateAndTime = reservationStartDateAndTime;
        this.reservationEndDateAndTime = reservationEndDateAndTime;
    }

    public String getLocation() { return location; }

    public String getCarType() {
        return carType;
    }

    public String getReservationStartDateAndTime() {
        return reservationStartDateAndTime;
    }

    public String getReservationEndDateAndTime() {
        return reservationEndDateAndTime;
    }
}
