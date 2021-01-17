package com.crd.carrental.controllers;

import com.crd.carrental.database.*;
import com.crd.carrental.rentalportfolio.CarTypes;
import com.crd.carrental.rentalportfolio.StoreLocations;
import com.crd.carrental.rentalportfolio.StoreNames;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.Random;

/**********************************************************************************************************************
 * A web request handler.
 *
 * @author Michael Lewis
**********************************************************************************************************************/
@Controller
public class ReservationController {
    private StoreLocations location;
    private CarTypes carType;
    private Timestamp reservationStartDateAndTime;
    private Timestamp reservationEndDateAndTime;
    private String fName;
    private String lName;
    private String email;
    private long creditCard;
    private String reservationNumber;
    private String vin;
    private StoreNames storeName;

    public ReservationController() {}

    @MessageMapping("/request")
    @SendTo("/reservation/request")
    public ReservationResponse requestReservation(ReservationRequest reservationRequest) {
        this.location = reservationRequest.getLocation();
        this.carType = reservationRequest.getCarType();
        this.reservationStartDateAndTime = convertDateAndTime(reservationRequest.getReservationStartDateAndTime());
        this.reservationEndDateAndTime = convertDateAndTime(reservationRequest.getReservationEndDateAndTime());
        this.fName = reservationRequest.getfName();
        this.lName = reservationRequest.getlName();
        this.email = reservationRequest.getEmail();
        this.creditCard = reservationRequest.getCreditCard();

        if (isStartAndEndValid(reservationStartDateAndTime, reservationEndDateAndTime)) {
            SelectStrategy selector = new SelectInventory();

            ReservationResponse reservationResponse = selector.select(this);

            vin = reservationResponse.getVin();
            storeName = reservationResponse.getStoreName();

            return reservationResponse;
        } else {
            return new ReservationResponse("", null, false);
        }
    }

    private Timestamp convertDateAndTime(String dateAndTime) {
        return Timestamp.valueOf(dateAndTime.replaceAll("T", " ") + ":00");
    }

    // A valid reservation request can only happen if the date and time are in the future and if the end date and time
    // are greater than the start date and time
    private boolean isStartAndEndValid(Timestamp startDateAndTime, Timestamp endDateAndTime) {
        Timestamp current = Timestamp.valueOf(new Timestamp(System.currentTimeMillis()).toString());

        return startDateAndTime.compareTo(endDateAndTime) <= 0 && current.compareTo(startDateAndTime) <= 0;
    }

    @MessageMapping("/confirmation")
    @SendTo("/reservation/confirmation")
    public ReservationConfirmation confirmReservation() {

        reservationNumber = getSaltString();

        updateInventoryTable();
        updateCustomerTable();

        return new ReservationConfirmation(reservationNumber);
    }

    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    private void updateInventoryTable() {
        InsertStrategy inventoryTable = new InsertReservationAgainstInventory();
        inventoryTable.insert(this);
    }

    private void updateCustomerTable() {
        InsertStrategy customerTable = new InsertCustomers();
        customerTable.insert(this);
    }

    public StoreLocations getLocation() {
        return location;
    }

    public CarTypes getCarType() {
        return carType;
    }

    public Timestamp getReservationStartDateAndTime() {
        return reservationStartDateAndTime;
    }

    public Timestamp getReservationEndDateAndTime() {
        return reservationEndDateAndTime;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public long getCreditCard() {
        return creditCard;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public String getVin() {
        return vin;
    }

    public StoreNames getStoreName() {
        return storeName;
    }
}

