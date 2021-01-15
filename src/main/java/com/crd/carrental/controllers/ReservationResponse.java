package com.crd.carrental.controllers;

import com.crd.carrental.rentalportfolio.StoreNames;

/**********************************************************************************************************************
 * Data model representing the response to the reservation request.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ReservationResponse {
    private String vin;
    private StoreNames storeName;
    private boolean isAvailable;

    public ReservationResponse() {}

    public ReservationResponse(String vin, StoreNames storeName, boolean isAvailable) {
        this.vin = vin;
        this.storeName = storeName;
        this.isAvailable = isAvailable;
    }

    public String getVin() { return vin; }

    public StoreNames getStoreName() {
        return storeName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
