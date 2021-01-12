package com.crd.carrental.database;

/**********************************************************************************************************************
 * Declares an interface common to all supported database update operations algorithms. Concrete strategies must
 * implement this interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public interface UpdateStrategy {

    void update(String vin);
}
