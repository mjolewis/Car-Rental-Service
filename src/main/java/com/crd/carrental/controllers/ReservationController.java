package com.crd.carrental.controllers;

import com.crd.carrental.database.*;
import com.crd.carrental.rentalportfolio.CarTypes;
import com.crd.carrental.rentalportfolio.StoreLocations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

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
    private String reservationStartDateAndTime;
    private String reservationEndDateAndTime;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private long creditCardNumber;
    private String reservationNumber;
    private ReservationResponse reservationResponse;

    public ReservationController() {}

    @MessageMapping("/request")
    @SendTo("/reservation/request")
    public ReservationResponse requestReservation(ReservationRequest reservationRequest) {
        this.location = reservationRequest.getLocation();
        this.carType = reservationRequest.getCarType();
        this.reservationStartDateAndTime = reservationRequest.getReservationStartDateAndTime();
        this.reservationEndDateAndTime = reservationRequest.getReservationEndDateAndTime();
        this.firstName = reservationRequest.getFirstName();
        this.lastName = reservationRequest.getLastName();
        this.emailAddress = reservationRequest.getEmailAddress();
        this.creditCardNumber = reservationRequest.getCreditCardNumber();


        SelectStrategy selector = new SelectInventory();
        reservationResponse = selector.select(this);
        return reservationResponse;
    }

    // If the car is available and the customer confirms the request, then reserve the car for the customer
    @MessageMapping("/confirmation")
    @SendTo("/reservation/confirmation")
    public ReservationConfirmation confirmReservation() {

        updateInventoryTable();
        updateCustomerTable();

        reservationNumber = getSaltString();

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
        UpdateStrategy inventoryTable = new UpdateInventoryTable();
        inventoryTable.update(this);
    }

    private void updateCustomerTable() {
        InsertCustomers customerTable = new InsertCustomers();
        customerTable.update(this);
    }


    public String getVin() {return reservationResponse.getVin(); }

    public StoreLocations getLocation() {
        return location;
    }

    public CarTypes getCarType() {
        return carType;
    }

    public String getReservationStartDateAndTime() {
        return reservationStartDateAndTime;
    }

    public String getReservationEndDateAndTime() {
        return reservationEndDateAndTime;
    }

    public ReservationResponse getReservationResponse() {
        return reservationResponse;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public long getCreditCardNumber() {
        return creditCardNumber;
    }
}

