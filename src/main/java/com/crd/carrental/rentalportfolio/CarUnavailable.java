package com.crd.carrental.rentalportfolio;

/**********************************************************************************************************************
 * Data model representing instances when there are no cars available in the database that match the customers
 * requirements.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class CarUnavailable extends RentalComponent {
    private StoreLocations location;
    private CarTypes carType;

    public CarUnavailable(StoreLocations location) {
        this.location = location;
        this.carType = CarTypes.Unavailable;
    }

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
        return true;
    }

    @Override
    public boolean isAvailable() { return false; }
}
