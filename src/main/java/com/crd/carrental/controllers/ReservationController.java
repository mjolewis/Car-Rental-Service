package com.crd.carrental.controllers;

import com.crd.carrental.database.SelectStrategy;
import com.crd.carrental.database.SelectInventory;
import com.crd.carrental.database.UpdateInventoryTable;
import com.crd.carrental.database.UpdateStrategy;
import com.crd.carrental.rentalportfolio.CarTypes;
import com.crd.carrental.rentalportfolio.StoreLocations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;

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
    private ReservationResponse reservationResponse;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String reservationNumber;
    private long creditCardNumber;

    public ReservationController() {}

    @MessageMapping("/location")
    public void setLocation(ReservationRequest reservationRequest) {
        this.location = reservationRequest.getLocation();
    }

    @MessageMapping("/carType")
    public void setCarType(ReservationRequest reservationRequest) {
        this.carType = reservationRequest.getCarType();
    }

    @MessageMapping("/start")
    public void setReservationStartDateAndTime(ReservationRequest reservationRequest) {
        this.reservationStartDateAndTime = reservationRequest.getReservationStartDateAndTime();
    }

    @MessageMapping("/end")
    public void setReservationEndDateAndTime(ReservationRequest reservationRequest) {
        this.reservationEndDateAndTime = reservationRequest.getReservationEndDateAndTime();
    }

    @MessageMapping("/request")
    @SendTo("/reservation/request/response")
    public ReservationResponse requestReservation() {
        SelectStrategy selector = new SelectInventory();
        reservationResponse = selector.select(location, carType, reservationStartDateAndTime, reservationEndDateAndTime);
        return reservationResponse;
    }

    // If the car is available and the customer confirms the request, then reserve the car for the customer
    @MessageMapping("/confirmation")
    @SendTo("/reservation/confirmation/response")
    public void confirmReservation() {
        String vin = reservationResponse.getVin();
        UpdateStrategy dbUpdator = new UpdateInventoryTable();
        dbUpdator.update(this);
    }

    public String getVin() {return reservationResponse.getVin(); }

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

