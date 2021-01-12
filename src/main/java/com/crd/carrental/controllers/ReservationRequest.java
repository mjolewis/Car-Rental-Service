package com.crd.carrental.controllers;

/**********************************************************************************************************************
 * Data model representing the users request to rent a car.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ReservationRequest {
    private String location;
    private String carType;
    private String dayOfRental;
    private String monthOfRental;
    private String numOfDaysToRent;

    public ReservationRequest() {
    }

    public ReservationRequest(String location, String carType, String dayOfRental, String monthOfRental,
                              String numOfDaysToRent) {
        this.location = location;
        this.carType = carType;
        this.dayOfRental = dayOfRental;
        this.monthOfRental = monthOfRental;
        this.numOfDaysToRent = numOfDaysToRent;
    }

    public String getLocation() { return location; }

    public String getCarType() {
        return carType;
    }

    public String getDayOfRental() {
        return dayOfRental;
    }

    public String getMonthOfRental() {
        return monthOfRental;
    }

    public String getNumOfDaysToRent() {
        return numOfDaysToRent;
    }
}
