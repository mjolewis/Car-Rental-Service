/**********************************************************************************************************************
 * Front-end for customers to enter new reservation requests or to check details of an existing reservation. The
 * front-end works with the Java Spring framework to establish and listen to routes for the customer requests.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/

/**
 * Establish a websocket connection and register subscribers.
 */
const socket = new SockJS('/reservationsystem');
const stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    $("#confirmation").html("");
    console.log('Connected: ' + frame);

    // Listens for a new reservation event
    stompClient.subscribe('/reservation/request', function (response) {
        let reservation = JSON.parse(response.body);
        if (reservation.available) {
            displayReservationConfirmation(reservation);
        } else {
            carWasNotFound();
        }
    });

    // Listens for a lookup reservation event
    stompClient.subscribe('/reservation/lookup', function (reservation) {
        const reservationWasFound = JSON.parse(reservation.body).start;
        if (reservationWasFound != null) {
            displayReservationDetails(JSON.parse(reservation.body));
        } else {
            displayInvalidReservationId();
        }
    })
});

/**
 * Send the customer information to the controller to check if a car is available.
 */
function sendReservationRequest() {
    const city = document.getElementById("city").value;
    const classification = document.getElementById("classification").value;
    const start = document.getElementById("start").value;
    const end = document.getElementById("end").value;
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const customerId = document.getElementById("customerId").value;
    const creditCardNumber = document.getElementById("creditCardNumber").value;

    stompClient.send("/app/request", {}, JSON.stringify(
        {
            'city': city,
            'classification': classification,
            'start': start,
            'end': end,
            'firstName': firstName,
            'lastName': lastName,
            'customerId': customerId,
            'creditCardNumber': creditCardNumber
        },));
}

/**
 * Echos reservation confirmation details to the customer.
 * @param reservation JSON object containing reservation details.
 */
function displayReservationConfirmation(reservation) {
    $("#confirmation").empty().append("<tr><td>"
        + "Confirmation Number:"
        + ' ' + reservation.reservationId
        + '<br/>Vehicle address:'
        + ' ' + reservation.streetNumber
        + ' ' + reservation.streetName
        + ', ' + reservation.city
        + ' ' + reservation.state
        + ', ' + reservation.zipCode
        + "</td></tr>");
}

/**
 * Echos message letting the customer know that the requested reservation cannot be made.
 */
function carWasNotFound() {
    $("#confirmation").empty().append("<tr><td>"
        + 'There are no cars available with your requirements. Please search again.'
        + "</td></tr>");
}

/**
 * Allows a customer to lookup reservation details for a specified reservationId.
 */
function lookupReservationDetails() {
    const reservationId = document.getElementById("reservationId").value;

    stompClient.send("/app/lookup", {}, JSON.stringify({'reservationId': reservationId}));
}

/**
 * Echos reservation confirmation details to the customer.
 * @param reservation JSON object containing reservation details.
 */
function displayReservationDetails(reservation) {
    $("#reservationDetails").empty().append(
        "<tr><td>"
        + 'Reservation Owner:'
        + ' ' + reservation.firstName
        + ' ' + reservation.lastName
        + '</br>From:'
        + ' ' + reservation.start
        + '</br>To:'
        + ' ' +reservation.end
        + '</br>Vehicle: '
        + ' ' + reservation.manufacturer
        + ' ' + reservation.model
        + '</br>Daily price: $'
        + reservation.dailyPrice
        + '</br>Vehicle address:'
        + ' ' + reservation.streetNumber
        + ' ' + reservation.streetName
        + ', ' + reservation.city
        + ' ' + reservation.state
        + ', ' + reservation.zipCode
        + "</td></tr>");
}

function displayInvalidReservationId() {
    $("#reservationDetails").empty().append("<tr><td>Invalid reservation number</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    // Enforce proper formatting for credit card numbers
    $('#creditCardNumber').on("keyup", function() {
        let foo = $(this).val().split("-").join(""); // remove hyphens
        if (foo.length > 0) {
            foo = foo.match(new RegExp('.{1,4}', 'g')).join("-");
        }
        $(this).val(foo);
    });

    $("#sendReservationRequest").on("click", function () {
        sendReservationRequest();
    });

    $("#lookupReservationDetails").on("click", function () {
        lookupReservationDetails();
    });
});