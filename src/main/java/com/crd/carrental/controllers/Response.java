package com.crd.carrental.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**********************************************************************************************************************
 * An interface for concrete Response objects that allows the re-use of common fields and accessors.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public abstract class Response {

    // Customer information
    String firstName;
    String lastName;

    // Vehicle information
    String manufacturer;
    String model;
    String vehicleId;
    BigDecimal dailyPrice;

    // Reservation information
    boolean isAvailable;
    String reservationId;
    Timestamp start;              // Timestamps are cloned to avoid returning immutable objects and exposing internals
    Timestamp end;                // Timestamps are cloned to avoid returning immutable objects and exposing internals

    // Store information
    String streetNumber;
    String streetName;
    String city;
    String state;
    String zipCode;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Timestamp getStart() {
        return start == null ? null : (Timestamp) start.clone();
    }

    public Timestamp getEnd() {
        return end == null ? null : (Timestamp) end.clone();
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }
}
