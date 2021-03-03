package com.crd.carrental.rentalportfolio.components;

import java.math.BigDecimal;
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

    public boolean isChild() {
        return false;
    }

    public String getVehicleId() {
        throw new UnsupportedOperationException();
    }

    public String getStoreId() {
        throw new UnsupportedOperationException();
    }

    public BigDecimal getDailyPrice() {
        throw new UnsupportedOperationException();
    }

    public String getClassification() {
        throw new UnsupportedOperationException();
    }

    public String getManufacturer() {
        throw new UnsupportedOperationException();
    }

    public String getModel() {
        throw new UnsupportedOperationException();
    }

    public int getNumberOfPassengers() {
        throw new UnsupportedOperationException();
    }

    public void print() {
        throw new UnsupportedOperationException();
    }
}
