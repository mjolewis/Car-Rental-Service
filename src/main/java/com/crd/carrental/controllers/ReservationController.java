package com.crd.carrental.controllers;

import com.crd.carrental.database.insertoperations.InsertCustomer;
import com.crd.carrental.database.insertoperations.InsertReservation;
import com.crd.carrental.database.insertoperations.InsertStrategy;
import com.crd.carrental.database.selectoperations.SelectExistingReservation;
import com.crd.carrental.database.selectoperations.SelectStrategy;
import com.crd.carrental.database.selectoperations.SelectAvailableReservation;
import com.crd.carrental.utils.DateAndTimeUtil;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;

/**********************************************************************************************************************
 * A web request handler. Handles customer requests for new and existing reservation events.
 *
 * @author Michael Lewis
 **********************************************************************************************************************/
@Controller
public class ReservationController {
    private String city;
    private String classification;
    private Timestamp start;
    private Timestamp end;
    private String firstName;
    private String lastName;
    private String customerId;
    private String creditCardNumber;
    private String vehicleId;
    private String reservationId;

    public ReservationController() {
    }

    @MessageMapping("/request")
    @SendTo("/reservation/request")
    public Response requestReservation(NewReservationRequest newReservationRequest) {
        this.city = newReservationRequest.getCity();
        this.classification = newReservationRequest.getClassification();
        this.start = DateAndTimeUtil.convertDateAndTime(newReservationRequest.getStart());
        this.end = DateAndTimeUtil.convertDateAndTime(newReservationRequest.getEnd());
        this.firstName = newReservationRequest.getFirstName();
        this.lastName = newReservationRequest.getLastName();
        this.customerId = newReservationRequest.getCustomerId();
        this.creditCardNumber = newReservationRequest.getCreditCardNumber();

        if (DateAndTimeUtil.isStartAndEndValid(start, end)) {
            Response newReservationResponse = isVehicleAvailableForReservation();

            if (newReservationResponse.isAvailable()) {
                // Extract attributes needed by insertReservation operation
                vehicleId = newReservationResponse.getVehicleId();
                reservationId = newReservationResponse.getReservationId();

                insertIntoReservationTable();
                insertIntoCustomerTable();

                return newReservationResponse;
            }
        }

        // This occurs if the start and end are invalid or if a reservation cannot be made
        return new NewReservationResponse(null, null, null,
                null, null, null, null, false);
    }

    private Response isVehicleAvailableForReservation() {
        SelectStrategy selector = new SelectAvailableReservation();
        return selector.select(this);
    }

    /**
     * Getter used by the SelectVehicle Strategy that checks for any conflicting reservations in the database.
     */
    public String getCity() {
        return city;
    }

    /**
     * Getter used by the SelectVehicle and InsertReservation Strategies. The SelectVehicle Strategy uses this getter
     * to ensure that the new reservation request does not conflict with an existing reservation in the database. The
     * InsertReservation Strategy uses this getter to persist relevant reservation details into the reservation table.
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * Getter used by the SelectVehicle and InsertReservation Strategies. The SelectVehicle Strategy uses this getter
     * to ensure that the new reservation request does not conflict with an existing reservation in the database. The
     * InsertReservation Strategy uses this getter to persist relevant reservation details into the reservation table.
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * Getter used by the SelectVehicle Strategy that checks for any conflicting reservations in the database.
     */
    public String getClassification() {
        return classification;
    }

    private void insertIntoReservationTable() {
        InsertStrategy reservationTable = new InsertReservation();
        reservationTable.insert(this);
    }

    /**
     * Getter used by the InsertReservation Strategy to ALTER the reservation table.
     */
    public String getReservationId() {
        return reservationId;
    }

    /**
     * Getter used by the InsertReservation Strategy to ALTER the reservation table.
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * Helper method used to insert this customer into the customer table.
     */
    private void insertIntoCustomerTable() {
        InsertStrategy customerTable = new InsertCustomer();
        customerTable.insert(this);
    }

    /**
     * Getter used by the InsertCustomer Strategy to ALTER the customer table.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter used by the InsertCustomer Strategy to ALTER the customer table.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter used by the InsertCustomer Strategy to ALTER the customer table.
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Getter used by the InsertCustomer Strategy to ALTER the customer table.
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    @MessageMapping("/lookup")
    @SendTo("/reservation/lookup")
    public Response lookupReservationDetails(ExistingReservationRequest request) {
        this.reservationId = request.getReservationId();
        SelectStrategy selector = new SelectExistingReservation();
        return selector.select(this);
    }
}

