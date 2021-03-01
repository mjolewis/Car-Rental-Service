package com.crd.carrental.controllers;

/**********************************************************************************************************************
 * Data model representing the users request to rent a vehicle.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public class NewReservationRequest {
    private String city;
    private String classification;
    private String start;
    private String end;
    private String firstName;
    private String lastName;
    private String customerId;
    private String creditCardNumber;

    public NewReservationRequest() {
    }

    public NewReservationRequest(String city, String classification, String start, String end, String firstName,
                                 String lastName, String customerId, String creditCardNumber) {

        this.city = city;
        this.classification = classification;
        this.start = start;
        this.end = end;
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerId = customerId;
        this.creditCardNumber = creditCardNumber;
    }

    public String getCity() {
        return city;
    }

    public String getClassification() {
        return classification;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }
}
