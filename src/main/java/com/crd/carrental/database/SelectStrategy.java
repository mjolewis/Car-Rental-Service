package com.crd.carrental.database;

import com.crd.carrental.controllers.ReservationResponse;
import com.crd.carrental.rentalportfolio.CarTypes;
import com.crd.carrental.rentalportfolio.StoreLocations;

import java.sql.Timestamp;

/**********************************************************************************************************************
 * Declares an interface common to all supported database Select algorithms. Concrete strategies must implement this
 * interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public interface SelectStrategy {

    ReservationResponse select(StoreLocations location, CarTypes carType, Timestamp reservationStartDateAndTime,
                               Timestamp reservationEndDateAndTime);
}
