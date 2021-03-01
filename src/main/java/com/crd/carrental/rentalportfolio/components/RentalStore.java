package com.crd.carrental.rentalportfolio.components;

import com.crd.carrental.rentalportfolio.storedata.StoreNames;

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
    private List<RentalComponent> carRentalPortfolio = new ArrayList<>();
    private Iterator<RentalComponent> iterator;

    public RentalStore(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public void add(RentalComponent rentalComponent) {
        carRentalPortfolio.add(rentalComponent);
    }

    @Override
    public void remove(RentalComponent rentalComponent) {
        carRentalPortfolio.remove(rentalComponent);
    }

    @Override
    public Iterator<RentalComponent> createIterator() {
        if (iterator == null) {
            iterator = new CompositeIterator(carRentalPortfolio.iterator());
        }
        return iterator;
    }

    @Override
    public String getStoreId() {
        return storeId;
    }

    @Override
    public RentalComponent getChild(int i) {
        return carRentalPortfolio.get(i);
    }

    @Override
    public void print() {
        System.out.println("\nStore ID: " + storeId);
        System.out.println("-------------");

        Iterator<RentalComponent> iterator = carRentalPortfolio.iterator();
        while (iterator.hasNext()) {
            RentalComponent rentalComponent = iterator.next();
            rentalComponent.print();
        }
    }
}
