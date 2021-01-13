package com.crd.carrental.controllers;

import com.crd.carrental.rentalportfolio.CarTypes;

/**********************************************************************************************************************
 * Data model representing the response to the reservation request.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ReservationResponse {
    private String vin;
    private CarTypes carType;
    private String location;
    private boolean isAvailable;

    public ReservationResponse() {}

    public ReservationResponse(String vin, CarTypes carType, String location, boolean isAvailable) {
        this.vin = vin;
        this.carType = carType;
        this.location = location;
        this.isAvailable = isAvailable;
    }

    public String getVin() { return vin; }

    public CarTypes getCarType() {
        return carType;
    }

    public String getLocation() {
        return location;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
