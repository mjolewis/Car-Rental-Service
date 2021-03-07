package com.crd.carrental.factories;

import com.crd.carrental.rentalportfolio.components.RentalComponent;
import com.crd.carrental.rentalportfolio.leafs.Camry;
import com.crd.carrental.rentalportfolio.leafs.Escape;
import com.crd.carrental.rentalportfolio.leafs.Sienna;
import com.crd.carrental.rentalportfolio.vehicledata.Vehicles;

/**********************************************************************************************************************
 * Supplies cars to our North East stores by implementing the CarFactory interface. Uses the Abstract Factory pattern.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class VehicleCreatorImpl implements VehicleCreator {
    private static volatile VehicleCreatorImpl instance;

    private VehicleCreatorImpl() {}

    public static VehicleCreatorImpl getInstance() {
        if (instance == null) {
            synchronized (VehicleCreatorImpl.class) {
                if (instance == null) {
                    instance = new VehicleCreatorImpl();
                }
            }
        }

        return instance;
    }

    @Override
    public RentalComponent createCar(Vehicles model, String vehicleId, String storeId) {
        switch (model) {
            case CAMRY:
                return new Camry(vehicleId, storeId);
            case ESCAPE:
                return new Escape(vehicleId, storeId);
            case SIENNA:
                return new Sienna(vehicleId, storeId);
            default:
                return null;
        }
    }
}