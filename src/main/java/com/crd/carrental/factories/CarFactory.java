package com.crd.carrental.factories;

import com.crd.carrental.rentalportfolio.vehicledata.Vehicles;
import com.crd.carrental.rentalportfolio.components.RentalComponent;

/**********************************************************************************************************************
 * Declares an interface common to all car creation classes Concrete factories must implement this interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public interface CarFactory {

    RentalComponent createCar(Vehicles model, String vehicleId, String storeId);
}
