package com.crd.carrental.rentalportfolio;

import java.util.Date;

/**********************************************************************************************************************
 * Data model for the leaf nodes within the car rental portfolio.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class Car extends RentalComponent {
    private String vin;
    private String location;
    private String carType;
    private boolean isRented;

    public Car(String vin, String location, String carType, boolean isRented) {
        this.vin = vin;
        this.location = location;
        this.carType = carType;
        this.isRented = isRented;
    }

    @Override
    public String getVin() {
        return vin;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getCarType() {
        return carType;
    }

    @Override
    public boolean isRented() {
        return isRented;
    }

    @Override
    public Date getReservationStartDate() {

    }

    @Override
    public Date getReservationEndDate() {

    }

    @Override
    public void print() {
        System.out.print("\n" + location);
        System.out.print(": " + vin);
        System.out.print(", " + carType);
        System.out.print(", " + isRented);
    }
}
