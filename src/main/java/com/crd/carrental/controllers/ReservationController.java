package com.crd.carrental.controllers;

import com.crd.carrental.database.SelectStrategy;
import com.crd.carrental.database.Select;
import com.crd.carrental.database.Update;
import com.crd.carrental.database.UpdateStrategy;
import com.crd.carrental.rentalportfolio.RentalComponent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**********************************************************************************************************************
 * A web request handler
 *
 * @author Michael Lewis
 * @version November 20, 2020 - Kickoff
**********************************************************************************************************************/
@Controller
public class ReservationController {
    private String location;
    private String carType;
    private String dayOfRental;
    private String monthOfRental;
    private String numOfDaysToRent;
    private RentalComponent car;

    public ReservationController() {}

    @MessageMapping("/location")
    public void setLocation(ReservationRequest reservationRequest) {
        this.location = reservationRequest.getLocation();
    }

    @MessageMapping("/carType")
    public void setCarType(ReservationRequest reservationRequest) {
        this.carType = reservationRequest.getCarType();
    }

    @MessageMapping("/day")
    public void setDayOfRental(ReservationRequest reservationRequest) {
        this.dayOfRental = reservationRequest.getDayOfRental();
    }

    @MessageMapping("/month")
    public void setMonthOfRental(ReservationRequest reservationRequest) {
        this.monthOfRental = reservationRequest.getMonthOfRental();
    }

    @MessageMapping("/rental/length")
    public void setNumOfDaysToRent(ReservationRequest reservationRequest) {
        this.numOfDaysToRent = reservationRequest.getNumOfDaysToRent();
    }

    @MessageMapping("/reservation/request")
    @SendTo("/reservation/response")
    public ReservationResponse requestReservationAndUpdateDatabase() {
        car = selectCar();
        boolean isRented = car.isRented();

        return new ReservationResponse(carType, location, isRented);
    }

    private RentalComponent selectCar() {
        SelectStrategy selector = new Select();
        return selector.select(location, carType);
    }

    // If the car is available and the customer confirms the request, then reserve the car for the customer
    @MessageMapping("/reservation/confirmation")
    @SendTo("/reservation/response")
    public void confirmReservation() {
        UpdateStrategy dbUpdator = new Update();
        String vin = car.getVin();
        dbUpdator.update(vin);
    }
}

