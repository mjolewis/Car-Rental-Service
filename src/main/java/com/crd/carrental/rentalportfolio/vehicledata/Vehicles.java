package com.crd.carrental.rentalportfolio.vehicledata;

import java.math.BigDecimal;

/**********************************************************************************************************************
 * A set of predefined vehicles. These vehicles are used by the concrete leaf nodes within the rental portfolio.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public enum Vehicles {
    CAMRY("Toyota", "Camry", VehicleClassification.SEDAN.getClassification(),
            new BigDecimal("75.00"), 3),
    ESCAPE("Ford", "Escape", VehicleClassification.SUV.getClassification(),
            new BigDecimal("125.00"), 4),
    SIENNA("Toyota", "Sienna", VehicleClassification.VAN.getClassification(),
            new BigDecimal("100.00"), 4);

    private final String manufacturer;
    private final String model;
    private final String classification;
    private final BigDecimal dailyPrice;
    private final int numberOfPassengers;

    Vehicles(final String manufacturer, final String model, final String classification,
             BigDecimal dailyPrice, int numberOfPassengers) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.classification = classification;
        this.dailyPrice = dailyPrice;
        this.numberOfPassengers = numberOfPassengers;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getClassification() {
        return classification;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }
}
