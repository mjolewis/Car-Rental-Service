package com.crd.carrental.database;

import com.crd.carrental.controllers.ReservationController;
import com.crd.carrental.controllers.ReservationResponse;

/**********************************************************************************************************************
 * Declares an interface common to all supported database Select algorithms. Concrete strategies must implement this
 * interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public interface SelectStrategy {

    ReservationResponse select(ReservationController controller);
}
