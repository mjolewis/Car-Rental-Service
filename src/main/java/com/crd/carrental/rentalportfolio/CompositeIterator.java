package com.crd.carrental.rentalportfolio;

import java.util.Iterator;
import java.util.Stack;

/**********************************************************************************************************************
 * A CompositeIterator is capable of iterating over an entire rental portfolio (e.g. stores and individual cars).
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class CompositeIterator implements Iterator<RentalComponent> {
    private Stack<Iterator<RentalComponent>> stack = new Stack<>();

    public CompositeIterator(Iterator<RentalComponent> iterator) {
        stack.push(iterator);
    }

    @Override
    public boolean hasNext() {
        if (stack.empty()) {
            return false;
        } else {
            Iterator<RentalComponent> iterator = stack.peek();
            if (iterator.hasNext()) {
                return true;
            } else {
                stack.pop();
                return hasNext();
            }
        }
    }

    @Override
    public RentalComponent next() {
        if (hasNext()) {
            Iterator<RentalComponent> iterator = stack.peek();
            RentalComponent component = iterator.next();

            stack.push(component.createIterator());

            return component;
        } else {
            return null;
        }
    }
}
