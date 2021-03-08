package com.crd.carrental.controllers;

/**********************************************************************************************************************
 * Data model representing the response to the reservation request.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class NewReservationResponse extends Response {

    public NewReservationResponse() {
    }

    public NewReservationResponse(String reservationId, String vehicleId, String streetNumber,
                                  String streetName, String city, String state,
                                  String zipCode, boolean isAvailable) {
        this.reservationId = reservationId;
        this.vehicleId = vehicleId;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.isAvailable = isAvailable;
    }
}
