package com.crd.carrental.rentalportfolio.vehicledata;

/**********************************************************************************************************************
 * A set of predefined vehicle classifications. Used by the Vehicle enum to ensure system consistency. The enums
 * provide a String representation of the manufacturer to avoid case conflicts with the data stored in the database
 * (e.g., Sedan versus SEDAN).
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public enum VehicleClassification {
    SEDAN("Sedan"),
    SUV("SUV"),
    VAN("Van");

    private final String classification;

    VehicleClassification(final String classification) {
        this.classification = classification;
    }

    public String getClassification() {
        return classification;
    }
}
