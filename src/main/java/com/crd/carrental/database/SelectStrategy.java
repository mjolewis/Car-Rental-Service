package com.crd.carrental.database;

import com.crd.carrental.rentalportfolio.RentalComponent;

/**********************************************************************************************************************
 * Declares an interface common to all supported database Select algorithms. Concrete strategies must implement this
 * interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public interface SelectStrategy {

    RentalComponent select(String location, String carType);
}
