package com.crd.carrental.rentalportfolio.vehicledata;

/**********************************************************************************************************************
 * A set of predefined vehicle manufacturers. Used by the Vehicles enum to ensure system consistency. The enums provide
 * a String representation of the manufacturer to avoid case conflicts with the data stored in the database (e.g.,
 * Toyota versus TOYOTA).
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public enum VehicleManufacturer {
    TOYOTA("Toyota"),
    FORD("Ford");

    private final String manufacturer;

    VehicleManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
