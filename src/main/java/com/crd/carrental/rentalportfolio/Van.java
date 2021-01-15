package com.crd.carrental.rentalportfolio;

/**********************************************************************************************************************
 * Data model for the SUV car type. SUV's are leaf nodes within the car rental portfolio.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class Van extends RentalComponent {
    private String vin;
    private StoreNames storeName;
    private StoreLocations location;
    private CarTypes carType;

    public Van(String vin, StoreNames storeName, StoreLocations location) {
        this.vin = vin;
        this.storeName = storeName;
        this.location = location;
        this.carType = CarTypes.Van;
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