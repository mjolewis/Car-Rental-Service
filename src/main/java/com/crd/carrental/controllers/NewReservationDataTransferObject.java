package com.crd.carrental.controllers;

/**********************************************************************************************************************
 * Data model representing the response to the reservation request.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class NewReservationDataTransferObject extends DataTransferObject {

    public NewReservationDataTransferObject() {
    }

    public NewReservationDataTransferObject(String reservationId, String vehicleId, String streetNumber,
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
