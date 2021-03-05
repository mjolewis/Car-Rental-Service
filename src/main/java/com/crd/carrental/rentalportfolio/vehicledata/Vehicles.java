package com.crd.carrental.rentalportfolio.vehicledata;

import java.math.BigDecimal;

/**********************************************************************************************************************
 * A set of predefined vehicles. These vehicles are used by the concrete leaf nodes within the rental portfolio.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public enum Vehicles {
    CAMRY(VehicleManufacturer.TOYOTA.getManufacturer(), "Camry", VehicleClassification.SEDAN.getClassification(),
            PriceSheet.LOW_END.getPrice(), 3, 0),
    ESCAPE(VehicleManufacturer.FORD.getManufacturer(), "Escape", VehicleClassification.SUV.getClassification(),
            PriceSheet.HIGH_END.getPrice(), 4, 0),
    SIENNA(VehicleManufacturer.TOYOTA.getManufacturer(), "Sienna", VehicleClassification.VAN.getClassification(),
            PriceSheet.MID_TIER.getPrice(), 4, 0);

    private final String manufacturer;
    private final String model;
    private final String classification;
    private final BigDecimal dailyPrice;
    private final int numberOfPassengers;
    private final long version;

    Vehicles(final String manufacturer, final String model, final String classification,
             BigDecimal dailyPrice, int numberOfPassengers, long version) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.classification = classification;
        this.dailyPrice = dailyPrice;
        this.numberOfPassengers = numberOfPassengers;
        this.version = version;
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

    public long getVersion() {
        return version;
    }
}
