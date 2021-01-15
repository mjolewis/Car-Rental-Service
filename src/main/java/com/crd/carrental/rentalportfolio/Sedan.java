package com.crd.carrental.rentalportfolio;

import java.sql.Timestamp;

/**********************************************************************************************************************
 * Data model for the Sedan car type. Sedan's are leaf nodes within the car rental portfolio.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class Sedan extends RentalComponent {
    private String vin;
    private StoreNames storeName;
    private StoreLocations location;
    private CarTypes carType;

    public Sedan(String vin, StoreNames storeName, StoreLocations location) {
        this.vin = vin;
        this.storeName = storeName;
        this.location = location;
        this.carType = CarTypes.Sedan;
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
    public void print() {
        System.out.print("\n" + location);
        System.out.print(": " + vin);
        System.out.print(", " + carType);
    }
}
