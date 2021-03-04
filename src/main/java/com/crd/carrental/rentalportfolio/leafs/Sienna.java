package com.crd.carrental.rentalportfolio.leafs;

import com.crd.carrental.rentalportfolio.components.RentalComponent;
import com.crd.carrental.rentalportfolio.vehicledata.Vehicles;
import java.math.BigDecimal;

/**********************************************************************************************************************
 * Data model for the SUV car type. SUV's are leaf nodes within the car rental portfolio.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class Sienna extends RentalComponent {
    private String vehicleId;
    private String storeId;
    private BigDecimal dailyPrice;
    private String classification;
    private String manufacturer;
    private String model;
    private int numberOfPassengers;
    private long version;

    public Sienna(String vehicleId, String storeId) {
        this.vehicleId = vehicleId;
        this.storeId = storeId;
        this.dailyPrice = Vehicles.SIENNA.getDailyPrice();
        this.classification = Vehicles.SIENNA.getClassification();
        this.manufacturer = Vehicles.SIENNA.getManufacturer();
        this.model = Vehicles.SIENNA.getModel();
        this.numberOfPassengers = Vehicles.SIENNA.getNumberOfPassengers();
        this.version = Vehicles.SIENNA.getVersion();
    }

    @Override
    public String getVehicleId() {
        return vehicleId;
    }

    @Override
    public String getStoreId() {
        return storeId;
    }

    @Override
    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    @Override
    public String getClassification() {
        return classification;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public boolean isChild() {
        return true;
    }

    @Override
    public void print() {
        System.out.print("\nVehicle ID: " + vehicleId);
        System.out.print("Store ID: " + storeId);
        System.out.print("Daily Price: " + dailyPrice);
        System.out.print("Classification: " + classification);
        System.out.print("Make: " + manufacturer);
        System.out.print("Model: " + model);
        System.out.println("Number of Passengers: " + numberOfPassengers);
    }
}