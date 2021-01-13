package com.crd.carrental.factories;

import com.crd.carrental.rentalportfolio.CarTypes;
import com.crd.carrental.rentalportfolio.RentalComponent;

/**********************************************************************************************************************
 * Declares an interface common to all car creation classes Concrete factories must implement this interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public interface CarFactory {

    RentalComponent createCar(CarTypes carType, String vin, String storeName, String location);
}
