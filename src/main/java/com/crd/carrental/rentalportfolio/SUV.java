package com.crd.carrental.rentalportfolio;

import java.sql.Timestamp;

/**********************************************************************************************************************
 * Data model for the SUV car type. SUV's are leaf nodes within the car rental portfolio.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class SUV extends RentalComponent {
    private String vin;
    private StoreNames storeName;
    private StoreLocations location;
    private CarTypes carType;
    private boolean isReserved;
    private boolean isAvailable;
    private Timestamp reservationStartDateAndTime;
    private Timestamp reservationEndDateAndTime;

    public SUV(String vin, StoreNames storeName, StoreLocations location, boolean isReserved, boolean isAvailable) {
        this.vin = vin;
        this.storeName = storeName;
        this.location = location;
        this.carType = CarTypes.SUV;
        this.isReserved = isReserved;
        this.isAvailable = isAvailable;
    }

    @Override
    public String getVin() {
        return vin;
    }

    @Override
    public StoreNames getStoreName() { return storeName; }

    @Override
    public boolean isChild() { return true; }

    @Override
    public StoreLocations getLocation() {
        return location;
    }

    @Override
    public CarTypes getCarType() {
        return carType;
    }

    @Override
    public boolean isReserved() {
        return isReserved;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public Timestamp getReservationStartDateAndTime() {
        return reservationStartDateAndTime;
    }

    @Override
    public Timestamp getReservationEndDateAndTime() {
        return reservationEndDateAndTime;
    }

    @Override
    public void print() {
        System.out.print("\n" + location);
        System.out.print(": " + vin);
        System.out.print(", " + carType);
        System.out.print(", " + isAvailable);
    }
}
