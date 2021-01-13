package com.crd.carrental.rentalportfolio;

/**********************************************************************************************************************
 * Data model representing instances when there are no cars available in the database that match the customers
 * requirements.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class CarUnavailable extends RentalComponent {
    private String location;
    private CarTypes carType;

    public CarUnavailable(String location) {
        this.location = location;
        this.carType = CarTypes.Unavailable;
    }

    @Override
    public String getLocation() {
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
