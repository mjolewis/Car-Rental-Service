package com.crd.carrental.rentalportfolio;

/**********************************************************************************************************************
 * Data model representing instances when there are no cars available in the database that match the customers
 * requirements.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class CarUnavailable extends RentalComponent {
    private String location;
    private String carType;

    public CarUnavailable(String location, String carType) {
        this.location = location;
        this.carType = carType;
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
        return true;
    }
}
