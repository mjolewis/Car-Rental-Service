package com.crd.carrental.controllers;

/**********************************************************************************************************************
 * Data model representing reservation confirmation details
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ReservationConfirmation {
    private String confirmationNumber;

    public ReservationConfirmation() {}

    public ReservationConfirmation(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }
}
