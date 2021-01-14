package com.crd.carrental.rentalportfolio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**********************************************************************************************************************
 * A Composite structure capable of recursively holding individual rental locations and individual cars.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class RentalStore extends RentalComponent {
    private StoreNames storeName;
    private List<RentalComponent> carRentalPortfolio = new ArrayList<>();
    private Iterator<RentalComponent> iterator;

    public RentalStore(StoreNames storeName) {
        this.storeName = storeName;
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
    public StoreNames getStoreName() {
        return storeName;
    }

    @Override
    public RentalComponent getChild(int i) {
        return carRentalPortfolio.get(i);
    }

    @Override
    public void print() {
        System.out.println("\n" + storeName);
        System.out.println("-------------");

        Iterator<RentalComponent> iterator = carRentalPortfolio.iterator();
        while (iterator.hasNext()) {
            RentalComponent rentalComponent = iterator.next();
            rentalComponent.print();
        }
    }
}
