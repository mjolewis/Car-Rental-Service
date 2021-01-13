package com.crd.carrental.rentalportfolio;

import java.sql.Timestamp;
import java.util.Iterator;

/**********************************************************************************************************************
 * An interface allowing for the composition of a part-whole tree based hierarchy of rental stores and the cars at
 * each store.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public abstract class RentalComponent {

    public void add(RentalComponent rentalComponent) {
        throw new UnsupportedOperationException();
    }

    public void remove(RentalComponent rentalComponent) {
        throw new UnsupportedOperationException();
    }

    public Iterator<RentalComponent> createIterator() {
        return new NullIterator();
    }

    public RentalComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    public String getStoreName() { throw new UnsupportedOperationException(); }

    public String getVin() {
        throw new UnsupportedOperationException();
    }

    public String getLocation() { throw new UnsupportedOperationException(); }

    public CarTypes getCarType() {
        throw new UnsupportedOperationException();
    }

    public boolean isReserved() {
        throw new UnsupportedOperationException();
    }

    public boolean isAvailable() {
        throw new UnsupportedOperationException();
    }

    public Timestamp getReservationStartDateAndTime() { throw new UnsupportedOperationException(); }

    public Timestamp getReservationEndDateAndTime() { throw new UnsupportedOperationException(); }

    public void print() {
        throw new UnsupportedOperationException();
    }
}
