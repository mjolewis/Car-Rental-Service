package com.crd.carrental.controllers;

import com.crd.carrental.rentalportfolio.CarTypes;
import com.crd.carrental.rentalportfolio.StoreLocations;

/**********************************************************************************************************************
 * Data model representing the response to the reservation request.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ReservationResponse {
    private String vin;
    private CarTypes carType;
    private StoreLocations location;
    private boolean isAvailable;

    public ReservationResponse() {}

    public ReservationResponse(String vin, CarTypes carType, StoreLocations location, boolean isAvailable) {
        this.vin = vin;
        this.carType = carType;
        this.location = location;
        this.isAvailable = isAvailable;
    }

    public String getVin() { return vin; }

    public StoreLocations getLocation() {
        return location;
    }

    public CarTypes getCarType() {
        return carType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
