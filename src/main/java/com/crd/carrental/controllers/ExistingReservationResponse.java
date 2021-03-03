package com.crd.carrental.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**********************************************************************************************************************
 * Data model representing reservation confirmation details.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class ExistingReservationResponse extends Response {

    public ExistingReservationResponse() {}

    public ExistingReservationResponse(String firstName, String lastName, Timestamp start, Timestamp end,
                                       String manufacturer, String model, BigDecimal dailyPrice, String streetNumber,
                                       String streetName, String city, String state, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.start = (start == null) ? null : (Timestamp) start.clone();
        this.end = (end == null) ? null : (Timestamp) end.clone();
        this.manufacturer = manufacturer;
        this.model = model;
        this.dailyPrice = dailyPrice;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}
