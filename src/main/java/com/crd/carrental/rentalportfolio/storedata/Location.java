package com.crd.carrental.rentalportfolio.storedata;

/**********************************************************************************************************************
 * A set of location constants used to represent the locations of our stores.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public enum Location {
    Boston("MA"),
    Cambridge("MA"),
    Burlington("MA");

    private final String state;

    Location(final String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
