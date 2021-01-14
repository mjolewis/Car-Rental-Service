package com.crd.carrental.factories;

import com.crd.carrental.rentalportfolio.CarTypes;
import com.crd.carrental.rentalportfolio.RentalComponent;
import com.crd.carrental.rentalportfolio.StoreLocations;
import com.crd.carrental.rentalportfolio.StoreNames;

/**********************************************************************************************************************
 * Declares an interface common to all car creation classes Concrete factories must implement this interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public interface CarFactory {

    RentalComponent createCar(CarTypes carType, String vin, StoreNames storeName, StoreLocations location);
}
