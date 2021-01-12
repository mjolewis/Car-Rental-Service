package com.crd.carrental.rentalportfolio;

import java.util.Iterator;

/**********************************************************************************************************************
 * Used by the leaf nodes in the Composite pattern to ensure that clients can avoid implementing null checks.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class NullIterator implements Iterator<RentalComponent> {

    public RentalComponent next() {
        return null;
    }

    public boolean hasNext() {
        return false;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
