package com.crd.carrental.controllers;

/**********************************************************************************************************************
 * Data model representing the users request to lookup an existing reservation.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ExistingReservationRequest {
    private String reservationId;

    public ExistingReservationRequest() {}

    public ExistingReservationRequest(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationId() {
        return reservationId;
    }
}
