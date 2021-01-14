package com.crd.carrental.factories;

import com.crd.carrental.rentalportfolio.*;

/**********************************************************************************************************************
 * Supplies cars to our North East stores by implementing the CarFactory interface. Uses the Abstract Factory pattern.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class NorthEastCarSupplier implements CarFactory {

    public NorthEastCarSupplier() {
    }

    @Override
    public RentalComponent createCar(CarTypes carType, String vin, StoreNames storeName, StoreLocations location) {
        switch(carType) {
            case Sedan:
                return new Sedan(vin, storeName, location, false, true);
            case SUV:
                return new SUV(vin, storeName, location, false, true);
            case Van:
                return new Van(vin, storeName, location, false, true);
            default:
                return new CarUnavailable(location);
        }
    }
}