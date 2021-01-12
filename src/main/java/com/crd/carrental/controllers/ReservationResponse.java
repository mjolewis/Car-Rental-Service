package com.crd.carrental.controllers;

/**********************************************************************************************************************
 * Data model representing the response to the reservation request.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ReservationResponse {
    private String carType;
    private String location;
    private boolean isAvailable;

    public ReservationResponse() {}

    public ReservationResponse(String carType, String location, boolean isAvailable) {
        this.carType = carType;
        this.location = location;
        this.isAvailable = isAvailable;
    }

    public String getCarType() {
        return carType;
    }

    public String getLocation() {
        return location;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
