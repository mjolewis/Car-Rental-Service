package com.crd.carrental.database;

import com.crd.carrental.rentalportfolio.RentalComponent;

/**********************************************************************************************************************
 * Declares an interface common to all supported database insert algorithms. Concrete strategies must implement this
 * interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public interface InsertStrategy {

    void insert(RentalComponent carsAvailableToRent);

}