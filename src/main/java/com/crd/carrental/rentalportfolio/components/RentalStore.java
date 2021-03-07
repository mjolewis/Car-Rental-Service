package com.crd.carrental.rentalportfolio.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**********************************************************************************************************************
 * A Composite structure capable of recursively holding individual rental locations and individual cars.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class RentalStore extends RentalComponent {
    private String storeId;
    private List<RentalComponent> branches = new ArrayList<>();
    private Iterator<RentalComponent> iterator;

    public RentalStore(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public void add(RentalComponent rentalComponent) {
        branches.add(rentalComponent);
    }

    @Override
    public void remove(RentalComponent rentalComponent) {
        branches.remove(rentalComponent);
    }

    @Override
    public Iterator<RentalComponent> createIterator() {
        if (iterator == null) {
            iterator = new CompositeIterator(branches.iterator());
        }
        return iterator;
    }

    @Override
    public String getStoreId() {
        return storeId;
    }

    @Override
    public RentalComponent getChild(int i) {
        return branches.get(i);
    }

    @Override
    public void print() {
        System.out.println("\nStore ID: " + storeId);
        System.out.println("-------------");

        Iterator<RentalComponent> iterator = branches.iterator();
        while (iterator.hasNext()) {
            RentalComponent rentalComponent = iterator.next();
            rentalComponent.print();
        }
    }
}
