package com.crd.carrental.controllers;

import com.crd.carrental.database.insertoperations.InsertCustomerStrategy;
import com.crd.carrental.database.insertoperations.InsertReservationStrategy;
import com.crd.carrental.database.insertoperations.InsertStrategy;
import com.crd.carrental.database.selectoperations.SelectAvailableReservation;
import com.crd.carrental.database.selectoperations.SelectStrategy;
import com.crd.carrental.utils.DateAndTimeUtil;
import java.sql.Timestamp;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**********************************************************************************************************************
 * A web request handler for new reservation events.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Controller
public class NewReservationController {
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
    private SelectStrategy selector = new SelectAvailableReservation();
    private InsertStrategy reservationStrategy = new InsertReservationStrategy();
    private InsertStrategy customerStrategy = new InsertCustomerStrategy();

    public NewReservationController() {
    }

    @MessageMapping("/request")
    @SendTo("/reservation/request")
    public DataTransferObject requestReservation(NewReservationRequest request) {
        this.city = request.getCity();
        this.classification = request.getClassification();
        this.start = DateAndTimeUtil.convertDateAndTime(request.getStart());
        this.end = DateAndTimeUtil.convertDateAndTime(request.getEnd());
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.customerId = request.getCustomerId();
        this.creditCardNumber = request.getCreditCardNumber();

        if (DateAndTimeUtil.isStartAndEndValid(start, end)) {
            DataTransferObject dataTransferObject = isVehicleAvailableForReservation();

            if (dataTransferObject.isAvailable()) {
                // Extract attributes needed by insertReservation operation
                vehicleId = dataTransferObject.getVehicleId();
                reservationId = dataTransferObject.getReservationId();

                insertIntoReservationTable();
                insertIntoCustomerTable();

                return dataTransferObject;
            }
        }

        // This occurs if the start and end are invalid or if a reservation isn't available
        return new NewReservationDataTransferObject(null, null, null,
                null, null, null, null, false);
    }

    private DataTransferObject isVehicleAvailableForReservation() {
        return selector.select(this);
    }

    /**
     * Getter used by the SelectVehicle Strategy that checks for any conflicting reservations in the database.
     */
    public String getCity() {
        return city;
    }

    /**
     * Getter used by the SelectVehicle Strategy that checks for any conflicting reservations in the database.
     */
    public String getClassification() {
        return classification;
    }

    /**
     * Getter used by the SelectVehicle and InsertReservation Strategies. The SelectVehicle Strategy uses this getter
     * to ensure that the new reservation request does not conflict with an existing reservation in the database. The
     * InsertReservation Strategy uses this getter to persist relevant reservation details into the reservation table.
     */
    public Timestamp getStart() {
        return (Timestamp) start.clone();
    }

    /**
     * Getter used by the SelectAvailableReservation and InsertReservation Strategies. The SelectAvailableReservation
     * Strategy uses this getter to ensure that the new reservation request does not conflict with an existing
     * reservation in the database. The InsertReservation Strategy uses this getter to persist relevant reservation
     * details into the reservation table.
     */
    public Timestamp getEnd() {
        return (Timestamp) end.clone();
    }

    private void insertIntoReservationTable() {
        reservationStrategy.insert(this);
    }

    /**
     * Getter used by the InsertReservation Strategy to ALTER the reservation table.
     */
    public String getReservationId() {
        return reservationId;
    }

    /**
     * Getter used by the InsertReservation and InsertCustomer Strategies. The InsertReservation Strategy uses this
     * getter to establish a the relation between the Customer table and the Reservation table. The InsertCustomer
     * Strategy uses this getter to ensure that a reservation is attached to the customer who requested it.
     */
    public String getCustomerId() {
        return customerId;
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
        customerStrategy.insert(this);
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
    public String getCreditCardNumber() {
        return creditCardNumber;
    }
}

