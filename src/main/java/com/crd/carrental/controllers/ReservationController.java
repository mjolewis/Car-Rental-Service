package com.crd.carrental.controllers;

import com.crd.carrental.database.SelectStrategy;
import com.crd.carrental.database.Select;
import com.crd.carrental.database.Update;
import com.crd.carrental.database.UpdateStrategy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**********************************************************************************************************************
 * A web request handler.
 *
 * @author Michael Lewis
**********************************************************************************************************************/
@Controller
public class ReservationController {
    private String location;
    private String carType;
    private String reservationStartDateAndTime;
    private String reservationEndDateAndTime;
    private String vin;

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
        SelectStrategy selector = new Select();
        ReservationResponse reservationResponse =
                selector.select(location, carType, reservationStartDateAndTime, reservationEndDateAndTime);

        vin = reservationResponse.getVin();

        return reservationResponse;
    }

    // If the car is available and the customer confirms the request, then reserve the car for the customer
    @MessageMapping("/confirmation")
    @SendTo("/reservation/confirmation/response")
    public void confirmReservation() {
        UpdateStrategy dbUpdator = new Update();
        dbUpdator.update(vin, reservationStartDateAndTime, reservationEndDateAndTime);
    }
}

