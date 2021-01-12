package com.crd.carrental.database;

/**********************************************************************************************************************
 * Declares an interface common to all supported database table creation algorithms. Concrete strategies must implement
 * this interface.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public interface TableStrategy {

    void createTableIfDoesntExist(String tableName);
}
