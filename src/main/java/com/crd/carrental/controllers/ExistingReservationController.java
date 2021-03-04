package com.crd.carrental.controllers;

import com.crd.carrental.database.selectoperations.SelectExistingReservation;
import com.crd.carrental.database.selectoperations.SelectStrategy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**********************************************************************************************************************
 * A web request handler for existing reservation events.
 *
 * @author Michael Lewis
 **********************************************************************************************************************/
@Controller
public class ExistingReservationController {
    private String reservationId;

    public ExistingReservationController() {}

    @MessageMapping("/lookup")
    @SendTo("/reservation/lookup")
    public DataTransferObject lookupReservationId(ExistingReservationRequest request) {
        this.reservationId = request.getReservationId();
        SelectStrategy selector = new SelectExistingReservation();
        return selector.select(this);
    }

    public String getReservationId() {
        return reservationId;
    }
}
