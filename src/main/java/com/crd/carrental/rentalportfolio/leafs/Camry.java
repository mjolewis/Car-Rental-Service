package com.crd.carrental.rentalportfolio.leafs;

import com.crd.carrental.rentalportfolio.components.RentalComponent;
import com.crd.carrental.rentalportfolio.vehicledata.Vehicles;
import java.math.BigDecimal;

/**********************************************************************************************************************
 * Data model for the Sedan car type. Sedan's are leaf nodes within the car rental portfolio.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class Camry extends RentalComponent {
    private String vehicleId;
    private String storeId;
    private BigDecimal dailyPrice;
    private String classification;
    private String manufacturer;
    private String model;
    private int numberOfPassengers;

    public Camry(String vehicleId, String storeId) {
        this.vehicleId = vehicleId;
        this.storeId = storeId;
        this.dailyPrice = Vehicles.CAMRY.getDailyPrice();
        this.classification = Vehicles.CAMRY.getClassification();
        this.manufacturer = Vehicles.CAMRY.getManufacturer();
        this.model = Vehicles.CAMRY.getModel();
        this.numberOfPassengers = Vehicles.CAMRY.getNumberOfPassengers();
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
